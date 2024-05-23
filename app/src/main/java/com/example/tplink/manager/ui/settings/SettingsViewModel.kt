package com.example.tplink.manager.ui.settings

import androidx.lifecycle.viewModelScope
import com.example.tplink.manager.logic.model.RequestModel
import com.example.tplink.manager.logic.model.RequestResponse
import com.example.tplink.manager.logic.network.Repository
import com.example.tplink.manager.logic.state.LoadingState
import com.example.tplink.manager.ui.base.BaseViewModel
import com.example.tplink.manager.util.encrypt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by bggRGjQaUbCoE on 2024/5/23
 */
class SettingsViewModel : BaseViewModel() {

    private val _requestResponse =
        MutableStateFlow<LoadingState<List<RequestResponse.AllPushMsg?>?>>(LoadingState.Loading)
    val requestResponse = _requestResponse.asStateFlow()

    private val _rebootResponse =
        MutableStateFlow<LoadingState<String>>(LoadingState.Loading)
    val rebootResponse = _rebootResponse.asStateFlow()

    private val _pwdResponse =
        MutableStateFlow<LoadingState<String>>(LoadingState.Loading)
    val pwdResponse = _pwdResponse.asStateFlow()

    private val _ledResponse =
        MutableStateFlow<LoadingState<String>>(LoadingState.Loading)
    val ledResponse = _ledResponse.asStateFlow()

    private val _natResponse =
        MutableStateFlow<LoadingState<String>>(LoadingState.Loading)
    val natResponse = _natResponse.asStateFlow()

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
        _ledResponse.value = LoadingState.Loading
        _natResponse.value = LoadingState.Loading
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

    fun getLEDStatus() {
        val data = RequestModel(
            method = "do",
            hyfi = RequestModel.Hyfi(
                getLedStatus = "null"
            )
        )
        viewModelScope.launch {
            Repository.getLEDStatus("stok=$stok/ds", data)
                .collect { result ->
                    _ledResponse.value = result
                }
        }
    }

    fun setLEDStatus(isOn: Boolean) {
        val data = RequestModel(
            method = "do",
            hyfi = RequestModel.Hyfi(
                setLedStatus = RequestModel.SetLedStatus(status = if (isOn) 0 else 1)
            )
        )
        viewModelScope.launch {
            Repository.status("stok=$stok/ds", data)
                .collect {
                }
        }
    }

    fun getNatStatus() {
        val data = RequestModel(
            method = "get",
            hnat = RequestModel.Hnat(
                name = "main"
            )
        )
        viewModelScope.launch {
            Repository.getNatStatus("stok=$stok/ds", data)
                .collect { result ->
                    _natResponse.value = result
                }
        }
    }

    fun setNatStatus(isOn: Boolean) {
        val data = RequestModel(
            method = "set",
            hnat = RequestModel.Hnat(
                main = RequestModel.Main(
                    enable = if (isOn) 0 else 1
                )
            )
        )
        viewModelScope.launch {
            Repository.status("stok=$stok/ds", data)
                .collect {
                }
        }
    }

}