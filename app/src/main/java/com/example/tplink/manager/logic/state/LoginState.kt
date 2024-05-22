package com.example.tplink.manager.logic.state

import com.example.tplink.manager.logic.exception.LoginException

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
sealed class LoginState {
    data object Loading : LoginState()
    data class Success(val stok: String) : LoginState()
    data class Error(val throwable: LoginException) : LoginState()
}