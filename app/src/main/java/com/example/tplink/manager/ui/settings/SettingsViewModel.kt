package com.example.tplink.manager.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tplink.manager.logic.model.RequestModel
import com.example.tplink.manager.logic.model.RequestResponse
import com.example.tplink.manager.logic.network.Repository
import com.example.tplink.manager.logic.state.LoadingState
import com.example.tplink.manager.util.PrefManager
import com.example.tplink.manager.util.encrypt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URLDecoder

/**
 * Created by bggRGjQaUbCoE on 2024/5/23
 */
class SettingsViewModel : ViewModel() {


    private val stok by lazy { URLDecoder.decode(PrefManager.stok, "UTF-8") }
    private val _requestResponse =
        MutableStateFlow<LoadingState<List<RequestResponse.AllPushMsg?>?>>(LoadingState.Loading)
    val requestResponse = _requestResponse.asStateFlow()

    private val _rebootResponse =
        MutableStateFlow<LoadingState<String>>(LoadingState.Loading)
    val rebootResponse = _rebootResponse.asStateFlow()

    private val _pwdResponse =
        MutableStateFlow<LoadingState<String>>(LoadingState.Loading)
    val pwdResponse = _pwdResponse.asStateFlow()

    fun getMessage() {
        val data = RequestModel(
            method = "get",
            system = RequestModel.System(
                table = "all_push_msg"
            )
        )
        viewModelScope.launch {
            Repository.getMessage("stok=$stok/ds", data)
                .collect { result ->
                    _requestResponse.value = result
                }
        }
    }

    fun reset() {
        _requestResponse.value = LoadingState.Loading
        _rebootResponse.value = LoadingState.Loading
        _pwdResponse.value = LoadingState.Loading
    }

    fun reboot() {
        val data = RequestModel(
            method = "do",
            system = RequestModel.System(
                reboot = ""
            )
        )
        viewModelScope.launch {
            Repository.status("stok=$stok/ds", data)
                .collect { result ->
                    _rebootResponse.value = result
                }
        }
    }

    fun changePwd(oldPwd: String, newPwd: String) {
        val data = RequestModel(
            method = "do",
            system = RequestModel.System(
                chgPwd = RequestModel.ChgPwd(
                    oldPwd = oldPwd.encrypt,
                    newPwd = newPwd.encrypt
                )
            )
        )
        viewModelScope.launch {
            Repository.status("stok=$stok/ds", data)
                .collect { result ->
                    _pwdResponse.value =
                        if (result is LoadingState.Success) LoadingState.Success(newPwd)
                        else result
                }
        }
    }

}