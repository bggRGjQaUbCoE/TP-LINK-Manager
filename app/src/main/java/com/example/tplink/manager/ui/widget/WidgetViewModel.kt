package com.example.tplink.manager.ui.widget

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
class WidgetViewModel:BaseViewModel() {

    private val _requestResponse =
        MutableStateFlow<LoadingState<List<RequestResponse.MarketPlugin?>?>>(LoadingState.Loading)
    val requestResponse = _requestResponse.asStateFlow()

    fun getPluginConfig() {
        viewModelScope.launch {
            val data = RequestModel(
                method = "do",
                pluginConfig = RequestModel.PluginConfig(
                    getMarketPlugin = ""
                )
            )
            viewModelScope.launch {
                Repository.getPluginConfig("stok=$stok/ds", data).collect { result ->
                    _requestResponse.value = result
                }
            }
        }
    }

}