package com.example.tplink.manager.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.example.tplink.manager.R
import com.example.tplink.manager.databinding.ActivityMainBinding
import com.example.tplink.manager.ui.base.BaseActivity
import com.example.tplink.manager.ui.login.LoginFragment

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTrans()

    }

    private fun initTrans() {
        if (supportFragmentManager.findFragmentById(R.id.container) == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, LoginFragment())
                .commit()
        }
    }

}