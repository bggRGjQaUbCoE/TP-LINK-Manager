package com.example.tplink.manager.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tplink.manager.logic.model.LoginModel
import com.example.tplink.manager.logic.network.Repository
import com.example.tplink.manager.logic.state.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
class MainViewModel : ViewModel() {


    private val _loginResponse = MutableStateFlow<LoginState>(LoginState.Loading)
    val loginResponse = _loginResponse.asStateFlow()

    fun postLogin(password: String) {
        val data = LoginModel(
            method = "do",
            login = LoginModel.Login(
                password = password
            )
        )
        viewModelScope.launch {
            Repository.postLogin(data).collect { result ->
                    _loginResponse.value = result
                }
        }
    }

}