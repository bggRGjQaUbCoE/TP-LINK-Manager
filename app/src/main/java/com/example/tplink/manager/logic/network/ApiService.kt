package com.example.tplink.manager.logic.network

import com.example.tplink.manager.logic.model.RequestModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url


/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
interface ApiService {

    @POST
    fun postRequest(
        @Url url:String,
        @Body data: RequestModel
    ): Call<ResponseBody>

    @GET
    fun download(@Url fileUrl: String): Call<ResponseBody>

}