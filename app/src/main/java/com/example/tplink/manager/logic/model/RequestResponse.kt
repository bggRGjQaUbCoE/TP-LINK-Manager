package com.example.tplink.manager.logic.model

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
data class RequestResponse(
    val status: String?,
    @SerializedName("error_code")
    val errorCode: String?,
    val stok: String?,
    val data: Data?,
    @SerializedName("hosts_info")
    val hostsInfo: HostsInfo?,
    val system: System?,
    val network: Network?,
    @SerializedName("market_plugin")
    val marketPlugin: List<Map<String, MarketPlugin?>?>?,
    val hnat: Hnat?
) {

    data class Hnat(
        val main: Main?
    )

    data class Main(
        val enable: Int?
    )

    data class MarketPlugin(
        @SerializedName("plugin_id")
        val pluginId: String?,
        val name: String?,
        val version: String?,
        val size: String?,
        @SerializedName("icon_url")
        val iconUrl: String?,
        @SerializedName("app_icon_url")
        val appIconUrl: String?,
        @SerializedName("local_app_icon_url")
        val localAppIconUrl: String?,
        @SerializedName("webzip_url")
        val webZipUrl: String?,
        @SerializedName("local_webzip_url")
        val localWebZipUrl: String?,
        @SerializedName("desc_url")
        val descUrl: String?,
        val tag: String?,
        @SerializedName("update_log")
        val updateLog: String?,
        val author: String?,
        @SerializedName("release_time")
        val releaseTime: String?,
        val property: String?,
        @SerializedName("opera_type")
        val operaType: String?,
        val status: Int?,
        @SerializedName("app_desc_url")
        val appDescUrl: String?,
        @SerializedName("can_update")
        val canUpdate: String?
    ) {
        override fun equals(other: Any?): Boolean = false

        override fun hashCode(): Int {
            return Random.nextInt()
        }
    }

    data class Network(
        @SerializedName("wan_status")
        val wanStatus: WanStatus?,
        val lan: Lan?
    )

    data class Lan(
        val ipaddr: String?,
        val netmask: String?,
        @SerializedName("fac_ipaddr")
        val facIpaddr: String?,
        @SerializedName("fac_netmask")
        val facNetmask: String?,
        @SerializedName("ip_mode")
        val ipMode: String?,
        val maxaddr: String?,
    )

    data class WanStatus(
        val ipaddr: String?,
        val netmask: String?,
        val gateway: String?,
        @SerializedName("pri_dns")
        val priDns: String?,
        @SerializedName("snd_dns")
        val sndDns: String?,
        @SerializedName("link_status")
        val linkStatus: Int?,
        @SerializedName("error_code")
        val errorCode: Int?,
        val proto: String?,
        @SerializedName("up_time")
        val upTime: Int?,
        @SerializedName("up_speed")
        val upSpeed: Int?,
        @SerializedName("down_speed")
        val downSpeed: Int?,
        @SerializedName("phy_status")
        val phyStatus: Int?
    )

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

    data class
    HostsInfo(
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
    ) {
        override fun equals(other: Any?): Boolean = false

        override fun hashCode(): Int {
            return Random.nextInt()
        }
    }

}
