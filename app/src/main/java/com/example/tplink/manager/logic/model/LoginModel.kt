package com.example.tplink.manager.logic.model

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
data class LoginModel(
    val method: String,
    val login: Login
) {
    data class Login(
        val password: String
    )
}