package com.example.tplink.manager.logic.network

import com.example.tplink.manager.logic.model.LoginModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
interface ApiService {

    @POST("/")
    fun postLogin(
        @Body data: LoginModel
    ): Call<ResponseBody>

}