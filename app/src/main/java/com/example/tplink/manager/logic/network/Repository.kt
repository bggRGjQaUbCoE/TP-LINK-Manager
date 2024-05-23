package com.example.tplink.manager.logic.network

import com.example.tplink.manager.logic.exception.LoginException
import com.example.tplink.manager.logic.model.RequestModel
import com.example.tplink.manager.logic.model.RequestResponse
import com.example.tplink.manager.logic.state.LoadingState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
object Repository {

    fun postLogin(url: String, data: RequestModel) = flow {
        try {
            val responseRaw = Network.postRequest(url, data).body()?.string()

           // Log.i("postLogin", "postLogin: responseRaw: $responseRaw")

            val response = Gson().fromJson(responseRaw, RequestResponse::class.java)

            if (response.errorCode == "0" && !response.stok.isNullOrEmpty()) {
                emit(LoadingState.Success(response.stok))
            } else {
                emit(LoadingState.Error(LoginException(response.errorCode.toString())))
            }
        } catch (e: Exception) {
            emit(LoadingState.Error(LoginException(e.message ?: "unknown error")))
        }
    }.flowOn(Dispatchers.IO)

    fun getDevices(url: String, data: RequestModel) = flow {
        try {
            val responseRaw = Network.postRequest(url, data).body()?.string()

            val response = Gson().fromJson(responseRaw, RequestResponse::class.java)

            if (response.errorCode == "0") {
                val list = response.hostsInfo?.hostInfo?.map {
                    it?.get(it.keys.first())
                }
                emit(LoadingState.Success(list))
            } else {
                emit(LoadingState.Error(LoginException(response.errorCode.toString())))
            }
        } catch (e: Exception) {
            emit(LoadingState.Error(LoginException(e.message ?: "unknown error")))
        }
    }.flowOn(Dispatchers.IO)

    fun blockDevice(url: String, data: RequestModel)= flow {
        try {
            val responseRaw = Network.postRequest(url, data).body()?.string()

            val response = Gson().fromJson(responseRaw, RequestResponse::class.java)

            if (response.errorCode == "0") {
                emit(LoadingState.Success("0"))
            } else {
                emit(LoadingState.Error(LoginException(response.errorCode.toString())))
            }
        } catch (e: Exception) {
            emit(LoadingState.Error(LoginException(e.message ?: "unknown error")))
        }
    }.flowOn(Dispatchers.IO)

}

