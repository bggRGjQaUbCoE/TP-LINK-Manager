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

    fun postLogin(url: String, data: RequestModel) = Flow(url, data) { response ->
        if (response.stok.isNullOrEmpty())
            LoadingState.Error(LoginException("stok is null or empty"))
        else
            LoadingState.Success(response.stok)
    }

    fun getDevices(url: String, data: RequestModel) = Flow(url, data) { response ->
        val list = response.hostsInfo?.hostInfo?.map {
            it?.get(it.keys.first())
        }
        LoadingState.Success(list)
    }

    fun status(url: String, data: RequestModel) = Flow(url, data) {
        LoadingState.Success("0")
    }

    fun getMessage(url: String, data: RequestModel) = Flow(url, data) { response ->
        val list = response.system?.allPushMsg?.map {
            it?.get(it.keys.first())
        }
        LoadingState.Success(list)
    }

    fun getNetwork(url: String, data: RequestModel) = Flow(url, data) { response ->
        LoadingState.Success(response.network)
    }

    fun getLEDStatus(url: String, data: RequestModel) = Flow(url, data) { response ->
        LoadingState.Success(response.status ?: "0")
    }

    fun getPluginConfig(url: String, data: RequestModel) = Flow(url, data) { response ->
        val list = response.marketPlugin?.map {
            it?.get(it.keys.first())
        }
        LoadingState.Success(list)
    }

    fun getNatStatus(url: String, data: RequestModel) = Flow(url, data) { response ->
        LoadingState.Success("${response.hnat?.main?.enable ?: 0}")
    }

    private fun <T> Flow(
        url: String,
        data: RequestModel,
        block: (RequestResponse) -> LoadingState<T>
    ) = flow {
        try {
            val responseRaw = Network.postRequest(url, data).body()?.string()

            val response = Gson().fromJson(responseRaw, RequestResponse::class.java)

            if (response.errorCode == "0") {
                emit(block(response))
            } else {
                emit(LoadingState.Error(LoginException(response.errorCode.toString())))
            }
        } catch (e: Exception) {
            emit(LoadingState.Error(LoginException(e.message ?: "unknown error")))
        }
    }.flowOn(Dispatchers.IO)

}

