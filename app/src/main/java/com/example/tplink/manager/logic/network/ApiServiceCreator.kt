package com.example.tplink.manager.logic.network

import com.example.tplink.manager.BuildConfig
import com.example.tplink.manager.util.PrefManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
object ApiServiceCreator {

    private val API_BASE_URL by lazy { "http://${PrefManager.host}" }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel
                (
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )
        )
        .build()


    private fun getRetrofit(host: String? = null): Retrofit = Retrofit.Builder()
        .baseUrl(if (host.isNullOrEmpty()) API_BASE_URL else "http://$host")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> create(host: String? = null, serviceClass: Class<T>): T =
        getRetrofit(host).create(serviceClass)

    inline fun <reified T> create(host: String? = null): T = create(host, T::class.java)


}