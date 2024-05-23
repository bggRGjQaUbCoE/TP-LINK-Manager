package com.example.tplink.manager.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tplink.manager.R
import com.example.tplink.manager.databinding.ActivityMainBinding
import com.example.tplink.manager.ui.base.BaseActivity
import com.example.tplink.manager.ui.login.LoginFragment

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        binding.navigationView.setupWithNavController(navHostFragment.navController)
    }

    fun showNav() {
        binding.navigationView.isVisible = true
    }

}