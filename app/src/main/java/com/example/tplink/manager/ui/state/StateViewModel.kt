package com.example.tplink.manager.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tplink.manager.logic.model.RequestModel
import com.example.tplink.manager.logic.model.RequestResponse
import com.example.tplink.manager.logic.network.Repository
import com.example.tplink.manager.logic.state.LoadingState
import com.example.tplink.manager.util.PrefManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URLDecoder

/**
 * Created by bggRGjQaUbCoE on 2024/5/23
 */
class StateViewModel : ViewModel() {

    var isInit = true

    private val stok by lazy { URLDecoder.decode(PrefManager.stok, "UTF-8") }
    private val _requestResponse =
        MutableStateFlow<LoadingState<List<RequestResponse.HostInfoDetail?>?>>(LoadingState.Loading)
    val requestResponse = _requestResponse.asStateFlow()

    fun getDevices() {
        viewModelScope.launch {
            val data = RequestModel(
                method = "get",
                hostsInfo = RequestModel.HostsInfo(
                    table = "host_info"
                )
            )
            viewModelScope.launch {
                Repository.getDevices("stok=$stok/ds", data).collect { result ->
                    _requestResponse.value = result
                }
            }
        }
    }

    fun blockDevice(position: Int, data: RequestModel) {
        viewModelScope.launch {
            viewModelScope.launch {
                Repository.status("stok=$stok/ds", data).collect { result ->
                    when (result) {
                        is LoadingState.Error -> _requestResponse.value = result
                        LoadingState.Loading -> {}
                        is LoadingState.Success -> {
                            _requestResponse.value =
                                LoadingState.Success(
                                    (_requestResponse.value as LoadingState.Success).response?.mapIndexed { index, hostInfoDetail ->
                                        if (index == position)
                                            hostInfoDetail?.copy(blocked = data.hostsInfo?.setBlockFlag?.isBlocked.toString())
                                        else
                                            hostInfoDetail
                                    } ?: emptyList()
                                )
                        }
                    }

                }
            }
        }
    }


}