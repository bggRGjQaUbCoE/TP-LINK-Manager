package com.example.tplink.manager.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tplink.manager.logic.model.RequestModel
import com.example.tplink.manager.logic.network.Repository
import com.example.tplink.manager.logic.state.LoadingState
import com.example.tplink.manager.util.encrypt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
class LoginViewModel : ViewModel() {

    private val _loginResponse = MutableStateFlow<LoadingState<String>>(LoadingState.Loading)
    val loginResponse = _loginResponse.asStateFlow()

    fun postLogin(password: String) {
        val data = RequestModel(
            method = "do",
            login = RequestModel.Login(
                password = password.encrypt
            )
        )
        viewModelScope.launch {
            Repository.postLogin("/", data).collect { result ->
                _loginResponse.value = result
            }
        }
    }

}