package com.example.tplink.manager.ui.settings.network

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tplink.manager.R
import com.example.tplink.manager.databinding.FragmentNetworkBinding
import com.example.tplink.manager.logic.state.LoadingState
import com.example.tplink.manager.ui.base.BaseFragment
import com.example.tplink.manager.ui.settings.MessageAdapter
import com.example.tplink.manager.util.makeToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by bggRGjQaUbCoE on 2024/5/23
 */
class NetworkFragment : BaseFragment<FragmentNetworkBinding>() {

    private val viewModel by viewModels<NetworkViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )

        initData()
        iniView()
        initObserve()

    }

    @SuppressLint("SetTextI18n")
    private fun initObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.requestResponse.collect { state ->
                    when (state) {
                        LoadingState.Loading -> {}

                        is LoadingState.Success -> {
                            val response = state.response
                            binding.networkType.text = response?.wanStatus?.proto
                            binding.title.text = if (response?.wanStatus?.errorCode == 0) "Normal"
                            else "Error"
                            binding.ip.text = "IP: ${response?.wanStatus?.ipaddr}"
                            binding.netmask.text = "Netmask: ${response?.wanStatus?.netmask}"
                            binding.gateway.text = "Gateway: ${response?.wanStatus?.gateway}"
                            binding.priDns.text = "DNS1: ${response?.wanStatus?.priDns}"
                            binding.sndDns.text = "DNS2: ${response?.wanStatus?.sndDns}"
                        }

                        is LoadingState.Error -> {
                            requireContext().makeToast("Loading Failed: ${state.throwable.errorMessage}")
                        }
                    }
                }
            }
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.action_networkFragment_to_settingsFragment)
        }
    }

    private fun initData() {
        if (viewModel.isInit) {
            viewModel.isInit = false
            viewModel.getData()
        }
    }

    private fun iniView() {
        binding.toolBar.apply {
            setNavigationIcon(R.drawable.outline_arrow_back_24)
            setNavigationOnClickListener {
                findNavController().navigate(R.id.action_networkFragment_to_settingsFragment)
            }
        }
        binding.network.setOnClickListener {

        }

        binding.advance.setOnClickListener {

        }
    }

}