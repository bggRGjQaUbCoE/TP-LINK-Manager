package com.example.tplink.manager.ui.state

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tplink.manager.databinding.FragmentStateBinding
import com.example.tplink.manager.logic.state.LoadingState
import com.example.tplink.manager.ui.base.BaseFragment
import com.example.tplink.manager.util.PrefManager
import com.example.tplink.manager.util.makeToast
import kotlinx.coroutines.launch

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
class StateFragment : BaseFragment<FragmentStateBinding>() {

    private val viewModel by viewModels<StateViewModel>()
    private lateinit var mAdapter: StateAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
        initObserve()

    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.requestResponse.collect { state ->
                    when (state) {
                        LoadingState.Loading -> {

                        }

                        is LoadingState.Success -> {
                            mAdapter.submitList(state.response)
                        }

                        is LoadingState.Error -> {
                            binding.info.text = state.throwable.errorMessage
                            requireContext().makeToast("Login Failed: ${state.throwable.errorMessage}")
                        }
                    }

                    binding.swipeRefresh.isRefreshing = false

                }
            }
        }
    }

    private fun initData() {
        if (viewModel.isInit) {
            viewModel.isInit = false
            viewModel.getDevices()
        }
    }

    private fun initView() {
        binding.logOut.setOnClickListener {
            PrefManager.autoLogin = false
            val intent = requireContext().packageManager.getLaunchIntentForPackage(requireContext().packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            requireContext().startActivity(intent)
        }

        binding.swipeRefresh.apply {
            setOnRefreshListener {
                viewModel.getDevices()
            }
        }

        mAdapter = StateAdapter { position, data ->
            viewModel.blockDevice(position, data)
        }
        mLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.apply {
            layoutManager = mLayoutManager
            adapter = mAdapter
        }
    }

}