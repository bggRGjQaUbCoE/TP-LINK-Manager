package com.example.tplink.manager.logic.network

import android.util.Log
import com.example.tplink.manager.logic.exception.LoginException
import com.example.tplink.manager.logic.model.LoginModel
import com.example.tplink.manager.logic.model.LoginResponse
import com.example.tplink.manager.logic.state.LoginState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
object Repository {

    fun postLogin(data: LoginModel) = flow {
        try {
            val responseRaw = Network.postLogin(data).body()?.string()

            Log.i("postLogin", "postLogin: responseRaw: $responseRaw")

            val response = Gson().fromJson(responseRaw, LoginResponse::class.java)

            if (response.errorCode == "0" && !response.stok.isNullOrEmpty()) {
                emit(LoginState.Success(response.stok))
            } else {
                emit(LoginState.Error(LoginException(response.errorCode.toString())))
            }
        } catch (e: Exception) {
            emit(LoginState.Error(LoginException(e.message ?: "unknown error")))
        }
    }.flowOn(Dispatchers.IO)

}

