package com.example.tplink.manager.util

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.tplink.manager.logic.network.ApiService
import com.example.tplink.manager.logic.network.ApiServiceCreator
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.URLDecoder
import java.util.Enumeration
import java.util.zip.ZipEntry
import java.util.zip.ZipFile


/**
 * Created by bggRGjQaUbCoE on 2024/5/24
 */

/**
 * 从assets目录中复制整个文件夹内容,考贝到 /data/data/包名/files/目录中
 * @param  activity  activity 使用CopyFiles类的Activity
 * @param  filePath  String  文件路径,如：/assets/aa
 */
fun copyAssetsDir2Phone(context: Context, filePath: String) {

    Log.i("asdasdasdasd", "copyAssetsDir2Phone: ")

    var filePath = filePath
    try {
        val fileList = context.assets.list(filePath)
        if (fileList?.isNotEmpty() == true) { //如果是目录
            val file = File(context.filesDir.absolutePath + File.separator + filePath)
            file.mkdirs() //如果文件夹不存在，则递归
            for (fileName in fileList) {
                filePath = filePath + File.separator + fileName

                copyAssetsDir2Phone(context, filePath)

                filePath = filePath.substring(0, filePath.lastIndexOf(File.separator))
                Log.e("oldPath", filePath)
            }
        } else { //如果是文件
            val inputStream = context.assets.open(filePath)
            val file = File(context.filesDir.absolutePath + File.separator + filePath)
            Log.i("copyAssets2Phone", "file:$file")
            if (!file.exists() || file.length() == 0L) {
                val fos = FileOutputStream(file)
                var len: Int
                val buffer = ByteArray(1024)
                while ((inputStream.read(buffer).also { len = it }) != -1) {
                    fos.write(buffer, 0, len)
                }
                fos.flush()
                inputStream.close()
                fos.close()
                Log.i("copyAssetsDir2Phone", "copyAssetsDir2Phone: 模型文件复制完毕")
            } else {
                Log.i("copyAssetsDir2Phone", "copyAssetsDir2Phone: 模型文件已存在，无需复制")
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

/**
 * 将文件从assets目录，考贝到 /data/data/包名/files/ 目录中。assets 目录中的文件，会不经压缩打包至APK包中，使用时还应从apk包中导出来
 * @param fileName 文件名,如aaa.txt
 */
fun copyAssetsFile2Phone(context: Context, fileName: String) {
    try {
        val inputStream = context.assets.open(fileName)
        //getFilesDir() 获得当前APP的安装路径 /data/data/包名/files 目录
        val file = File(context.filesDir.absolutePath + File.separator + fileName)
        if (!file.exists() || file.length() == 0L) {
            val fos = FileOutputStream(file) //如果文件不存在，FileOutputStream会自动创建文件
            var len: Int
            val buffer = ByteArray(1024)
            while ((inputStream.read(buffer).also { len = it }) != -1) {
                fos.write(buffer, 0, len)
            }
            fos.flush() //刷新缓存区
            inputStream.close()
            fos.close()
            Log.i("copyAssetsDir2Phone", "copyAssetsDir2Phone: 模型文件复制完毕")
        } else {
            Log.i("copyAssetsDir2Phone", "copyAssetsDir2Phone: 模型文件已存在，无需复制")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun downloadFile(
    id: String,
    cachePath: String,
    filePath: String,
    fileUrl: String,
    done: () -> Unit
) {

    val url = Uri.parse(URLDecoder.decode(fileUrl, "UTF-8"))

    val downloadService: ApiService = ApiServiceCreator.create<ApiService>(url.host)

    val call: Call<ResponseBody> = downloadService.download(url.path ?: "")
    call.enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    var inputStream: InputStream? = null
                    val buf = ByteArray(4096)
                    var len: Int
                    var fos: FileOutputStream? = null

                    // 储存下载文件的目录
                    try {
                        inputStream = it.byteStream()
                        val total = it.contentLength()
                        val file = File(cachePath, "${id}.zip")
                        fos = FileOutputStream(file)
                        var sum: Long = 0
                        while ((inputStream.read(buf).also { len = it }) != -1) {
                            fos.write(buf, 0, len)
                            sum += len.toLong()
                            // val progress = (sum * 1.0f / total * 100).toInt()
                            // 下载中
                        }
                        fos.flush()
                        upZipFile(id, file, filePath, done)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        try {
                            inputStream?.close()
                        } catch (e: IOException) {
                        }
                        try {
                            fos?.close()
                        } catch (e: IOException) {
                        }
                    }
                }
            } else {
                Log.d("writeResponseBodyToDisk", "服务器请求失败")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.d("writeResponseBodyToDisk", "网络不可用")
        }
    })
}

@Throws(java.lang.Exception::class)
fun upZipFile(id: String, zipFile: File, folderPath: String, done: () -> Unit) {
    try {
        val zfile = ZipFile(zipFile)
        val zList: Enumeration<*> = zfile.entries()
        var ze: ZipEntry? = null
        val buf = ByteArray(1024)
        while (zList.hasMoreElements()) {
            ze = zList.nextElement() as ZipEntry
            if (ze.isDirectory) {
                var dirstr = folderPath + ze.name
                dirstr = String(dirstr.toByteArray(charset("8859_1")), charset("GB2312"))
                val f = File(dirstr)
                f.mkdir()
                continue
            }
            val os: OutputStream = BufferedOutputStream(
                FileOutputStream(getRealFileName(folderPath, ze.name))
            )
            val inputStream: InputStream = BufferedInputStream(zfile.getInputStream(ze))
            var readLen: Int
            while ((inputStream.read(buf, 0, 1024).also { readLen = it }) != -1) {
                os.write(buf, 0, readLen)
            }
            inputStream.close()
            os.close()
        }
        zfile.close()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        done()
    }
}

/**
 * 给定根目录，返回一个相对路径所对应的实际文件名.
 *
 * @param baseDir     指定根目录
 * @param absFileName 相对路径名，来自于ZipEntry中的name
 * @return java.io.File 实际的文件
 */
fun getRealFileName(baseDir: String, absFileName: String): File {
    val dirs = absFileName.split("/".toRegex()).dropLastWhile { it.isEmpty() }
        .toTypedArray()
    var lastDir = baseDir
    if (dirs.size > 1) {
        for (i in 0 until dirs.size - 1) {
            lastDir += (dirs[i] + "/")
            val dir = File(lastDir)
            if (!dir.exists()) {
                dir.mkdirs()
            }
        }
        val ret = File(lastDir, dirs[dirs.size - 1])
        return ret
    } else {
        return File(baseDir, absFileName)
    }
}