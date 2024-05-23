package com.example.tplink.manager.ui.settings.network

import androidx.lifecycle.viewModelScope
import com.example.tplink.manager.logic.model.RequestModel
import com.example.tplink.manager.logic.model.RequestResponse
import com.example.tplink.manager.logic.network.Repository
import com.example.tplink.manager.logic.state.LoadingState
import com.example.tplink.manager.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by bggRGjQaUbCoE on 2024/5/23
 */
class NetworkViewModel : BaseViewModel() {

    private val _requestResponse =
        MutableStateFlow<LoadingState<RequestResponse.Network?>>(LoadingState.Loading)
    val requestResponse = _requestResponse.asStateFlow()

    fun getData() {
        val data = RequestModel(
            method = "get",
            network = RequestModel.ArrayString(
                listOf("wan_status", "lan")
            ),
            protocol = RequestModel.ArrayString(
                listOf("wan", "dhcp", "static", "pppoe")
            ),
            wireless = RequestModel.ArrayString(
                listOf("wlan_wds_2g", "wlan_wds_5g")
            )
        )
        viewModelScope.launch {
            Repository.getNetwork("stok=$stok/ds", data)
                .collect { result ->
                    _requestResponse.value = result
                }
        }
    }


}