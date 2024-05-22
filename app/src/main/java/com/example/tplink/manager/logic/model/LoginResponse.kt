package com.example.tplink.manager.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
data class LoginResponse(
    @SerializedName("error_code")
    val errorCode: String?,
    val stok: String?,
    val data: Data?
) {
    data class Data(
        val code: String?,
        val time: String?,
        val group: String?
    )
}
