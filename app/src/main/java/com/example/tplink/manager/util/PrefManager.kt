package com.example.tplink.manager.util

import android.content.Context.MODE_PRIVATE
import com.example.tplink.manager.TPLINKManager.Companion.context

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
object PrefManager {

    private val pref = context.getSharedPreferences("settings", MODE_PRIVATE)

    var host: String
        get() = pref.getString("host", "")!!
        set(value) = pref.edit().putString("host", value).apply()

    var account: String
        get() = pref.getString("account", "")!!
        set(value) = pref.edit().putString("account", value).apply()

    var password: String
        get() = pref.getString("password", "")!!
        set(value) = pref.edit().putString("password", value).apply()

    var stok: String
        get() = pref.getString("stok", "")!!
        set(value) = pref.edit().putString("stok", value).apply()

    var autoLogin: Boolean
        get() = pref.getBoolean("autoLogin", false)
        set(value) = pref.edit().putBoolean("autoLogin", value).apply()

}