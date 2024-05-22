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


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}