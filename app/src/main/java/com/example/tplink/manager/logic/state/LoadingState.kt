package com.example.tplink.manager.logic.state

import com.example.tplink.manager.logic.exception.LoginException

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
sealed class LoadingState<out T> {
    data object Loading : LoadingState<Nothing>()
    data class Success<out T>(val response: T) : LoadingState<T>()
    data class Error(val throwable: LoginException) : LoadingState<Nothing>()
}