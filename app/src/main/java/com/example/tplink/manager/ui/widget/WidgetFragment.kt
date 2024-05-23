package com.example.tplink.manager.ui.widget

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tplink.manager.databinding.FragmentWidgetBinding
import com.example.tplink.manager.logic.state.LoadingState
import com.example.tplink.manager.ui.base.BaseFragment
import com.example.tplink.manager.util.makeToast
import kotlinx.coroutines.launch

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
class WidgetFragment : BaseFragment<FragmentWidgetBinding>() {

    private val viewModel by viewModels<WidgetViewModel>()
    private lateinit var mAdapter: WidgetAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
        initObserve()

    }

    private fun initObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.requestResponse.collect { state ->
                    when (state) {
                        LoadingState.Loading -> {}

                        is LoadingState.Success -> {
                            mAdapter.submitList(state.response)
                        }

                        is LoadingState.Error -> {
                            binding.info.text = state.throwable.errorMessage
                            requireContext().makeToast("Loading Failed: ${state.throwable.errorMessage}")
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
            viewModel.getPluginConfig()
        }
    }

    private fun initView() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getPluginConfig()
        }

        mAdapter = WidgetAdapter {

        }
        binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}