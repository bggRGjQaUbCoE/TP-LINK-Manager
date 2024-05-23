package com.example.tplink.manager.ui.widget

import android.os.Bundle
import android.view.View
import com.example.tplink.manager.databinding.FragmentMainBinding
import com.example.tplink.manager.ui.base.BaseFragment
import com.example.tplink.manager.util.PrefManager

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
class WidgetFragment : BaseFragment<FragmentMainBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.stok.text = "WidgetFragment"

    }

}