package com.example.tplink.manager.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tplink.manager.R
import com.example.tplink.manager.databinding.FragmentSettingsBinding
import com.example.tplink.manager.logic.state.LoadingState
import com.example.tplink.manager.ui.base.BaseFragment
import com.example.tplink.manager.util.PrefManager
import com.example.tplink.manager.util.makeToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserve()

    }

    private fun initObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.requestResponse.collect { state ->
                    when (state) {
                        LoadingState.Loading -> {}

                        is LoadingState.Success -> {
                            with(state.response) {
                                if (this.isNullOrEmpty()) {
                                    requireContext().makeToast("message is null or empty")
                                } else {
                                    val recyclerView = RecyclerView(requireContext()).apply {
                                        layoutManager = LinearLayoutManager(requireContext())
                                        adapter = MessageAdapter().also {
                                            it.submitList(this@with)
                                        }
                                    }
                                    MaterialAlertDialogBuilder(requireContext()).apply {
                                        setTitle("Message")
                                        setView(recyclerView)
                                        setPositiveButton(android.R.string.ok, null)
                                        show()
                                    }
                                }
                            }
                            viewModel.reset()
                        }

                        is LoadingState.Error -> {
                            requireContext().makeToast("Loading Failed: ${state.throwable.errorMessage}")
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.rebootResponse.collect { state ->
                    when (state) {
                        LoadingState.Loading -> {}

                        is LoadingState.Success -> {
                            requireContext().makeToast("rebooting...")
                            viewModel.reset()
                        }

                        is LoadingState.Error -> {
                            requireContext().makeToast("Failed: ${state.throwable.errorMessage}")
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.pwdResponse.collect { state ->
                    when (state) {
                        LoadingState.Loading -> {}

                        is LoadingState.Success -> {
                            PrefManager.password = state.response
                            requireContext().makeToast("OK")
                            viewModel.reset()
                        }

                        is LoadingState.Error -> {
                            requireContext().makeToast("Failed: ${state.throwable.errorMessage}")
                        }
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.toolBar.apply {
            inflateMenu(R.menu.menu_settings)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.message -> {
                        viewModel.getMessage()
                        true
                    }

                    else -> false
                }
            }
        }
    }

}