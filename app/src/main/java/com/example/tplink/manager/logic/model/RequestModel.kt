package com.example.tplink.manager.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
data class RequestModel(
    val method: String,
    val login: Login? = null,
    @SerializedName("hosts_info")
    val hostsInfo: HostsInfo? = null,
    val system: System? = null
) {

    data class System(
        val table: String? = null,
        val reboot: String? = null,
        @SerializedName("chg_pwd")
        val chgPwd: ChgPwd? = null,
    )

    data class ChgPwd(
        @SerializedName("old_pwd")
        val oldPwd: String,
        @SerializedName("new_pwd")
        val newPwd: String
    )

    data class Login(
        val password: String
    )

    data class HostsInfo(
        val table: String? = null,
        @SerializedName("set_block_flag")
        val setBlockFlag: SetBlockFlag? = null
    )

    data class SetBlockFlag(
        val mac: String,
        @SerializedName("is_blocked")
        val isBlocked: Int,
        val name: String,
        @SerializedName("up_limit")
        val upLimit: Int,
        @SerializedName("down_limit")
        val downLimit: Int
    )

}