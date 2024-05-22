package com.example.tplink.manager.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tplink.manager.R
import com.example.tplink.manager.databinding.FragmentLoginBinding
import com.example.tplink.manager.logic.state.LoginState
import com.example.tplink.manager.ui.base.BaseFragment
import com.example.tplink.manager.ui.main.MainFragment
import com.example.tplink.manager.ui.main.MainViewModel
import com.example.tplink.manager.util.PrefManager
import com.example.tplink.manager.util.makeToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel by viewModels<MainViewModel>(
        ownerProducer = { requireActivity() }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initHost()
        initButton()
        initObserve()

    }

    private fun initView() {
        with(PrefManager.autoLogin) {
            binding.autoLogin.isChecked = this
            if (this) {
                binding.password.setText(PrefManager.password)
                viewModel.postLogin(PrefManager.password)
            }
        }
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.loginResponse.collect { state ->
                    when (state) {
                        LoginState.Loading -> {

                        }

                        is LoginState.Success -> {
                            PrefManager.stok = state.stok
                            requireActivity().supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.container, MainFragment())
                                .commit()

                            with(binding.autoLogin.isChecked) {
                                PrefManager.autoLogin = this
                                PrefManager.password =
                                    if (this) binding.password.text.toString()
                                    else ""
                            }
                        }

                        is LoginState.Error -> {
                            requireContext().makeToast("Login Failed: ${state.throwable.errorCode}")
                        }
                    }
                }
            }
        }
    }

    private fun initButton() {
        binding.login.setOnClickListener {
            viewModel.postLogin(binding.password.text.toString())
        }
    }

    private fun initHost() {
        val textView = AppCompatEditText(requireContext())
        if (PrefManager.host.isEmpty()) {
            MaterialAlertDialogBuilder(requireContext()).apply {
                setCancelable(false)
                setTitle(getString(R.string.set_host))
                setView(textView)
                setPositiveButton(android.R.string.ok) { _, _ ->
                    PrefManager.host = textView.text.toString()
                }
                show()
            }
        }
    }

}