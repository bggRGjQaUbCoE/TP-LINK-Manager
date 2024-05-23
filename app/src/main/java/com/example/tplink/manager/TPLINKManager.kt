package com.example.tplink.manager

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.google.android.material.color.DynamicColors

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
class TPLINKManager : Application() {

    override fun onCreate() {
        super.onCreate()

        DynamicColors.applyToActivitiesIfAvailable(this)
        context = applicationContext

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

}