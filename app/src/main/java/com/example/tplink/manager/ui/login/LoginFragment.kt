package com.example.tplink.manager.ui.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tplink.manager.R
import com.example.tplink.manager.databinding.FragmentLoginBinding
import com.example.tplink.manager.logic.state.LoadingState
import com.example.tplink.manager.ui.base.BaseFragment
import com.example.tplink.manager.ui.main.MainActivity
import com.example.tplink.manager.util.PrefManager
import com.example.tplink.manager.util.makeToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel by viewModels<LoginViewModel>(
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
            } else {
                binding.password.apply {
                    isFocusable = true
                    isFocusableInTouchMode = true
                    requestFocus()
                }
                lifecycleScope.launch {
                    delay(150)
                    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .showSoftInput(binding.password, 0)
                }
            }
        }
        binding.changeHost.setOnClickListener {
            initHost(true)
        }
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.loginResponse.collect { state ->
                    when (state) {
                        LoadingState.Loading -> {

                        }

                        is LoadingState.Success -> {
                            PrefManager.stok = state.response

                            val navController = findNavController()
                            navController.navigate(R.id.action_loginFragment_to_stateFragment)

                            (activity as? MainActivity)?.showNav()

                            with(binding.autoLogin.isChecked) {
                                PrefManager.autoLogin = this
                                PrefManager.password =
                                    if (this) binding.password.text.toString()
                                    else ""
                            }
                        }

                        is LoadingState.Error -> {
                            requireContext().makeToast("Login Failed: ${state.throwable.errorMessage}")
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

    private fun initHost(show: Boolean = false) {
        with(PrefManager.host) {
            if (show || this.isEmpty()) {
                val editText = AppCompatEditText(requireContext())
                if (this.isEmpty())
                    editText.hint = "192.168.0.1"
                else
                    editText.setText(this)
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setCancelable(false)
                    setTitle(getString(R.string.set_host))
                    setView(editText)
                    setPositiveButton(android.R.string.ok) { _, _ ->
                        PrefManager.host = editText.text.toString().ifEmpty { "192.168.0.1" }
                    }
                }.create().apply {
                    window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
                    editText.requestFocus()
                }.show()
            }
        }
    }

}