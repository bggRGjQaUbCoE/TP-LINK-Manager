package com.example.tplink.manager.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
data class RequestResponse(
    @SerializedName("error_code")
    val errorCode: String?,
    val stok: String?,
    val data: Data?,
    @SerializedName("hosts_info")
    val hostsInfo: HostsInfo?,
    val system: System?
) {

    data class System(
        @SerializedName("all_push_msg")
        val allPushMsg: List<Map<String, AllPushMsg?>?>?
    )

    data class AllPushMsg(
        val event: String?,
        val attribute: Attribute?
    )

    data class Attribute(
        val msgId: String?,
        val displayType: String?,
        val retainedMessageBar: String?,
        val eventType: String?,
        val content: String?,
        val encodeType: String?,
        val time: Long?,
        val mac: String?,
        val runtime: String?,
    )

    data class Data(
        val code: String?,
        val time: String?,
        val group: String?
    )

    data class HostsInfo(
        @SerializedName("host_info")
        val hostInfo: List<Map<String, HostInfoDetail?>?>?,
    )

    data class HostInfoDetail(
        val mac: String?,
        @SerializedName("parent_mac")
        val parentMac: String?,
        @SerializedName("is_mesh")
        val isMesh: String?,
        @SerializedName("wifi_mode")
        val wifiMode: String?,
        val type: String?,
        val blocked: String?,
        val ip: String?,
        val hostname: String?,
        @SerializedName("up_speed")
        val upSpeed: String?,
        @SerializedName("down_speed")
        val downSpeed: String?,
        @SerializedName("up_limit")
        val upLimit: String?,
        @SerializedName("down_limit")
        val downLimit: String?,
        @SerializedName("cfg_valid")
        val cfgValid: String?,
        @SerializedName("is_cur_host")
        val isCurHost: String?,
        val ssid: String?,
        @SerializedName("forbid_domain")
        val forbidDomain: String?,
        @SerializedName("limit_time")
        val limitTime: String?,
        // @SerializedName("plan_rule")
        // val planRule: List<>?
    )

}
