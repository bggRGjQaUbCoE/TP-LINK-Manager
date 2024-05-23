package com.example.tplink.manager.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceDataStore
import androidx.preference.PreferenceFragmentCompat
import com.example.tplink.manager.BuildConfig
import com.example.tplink.manager.R
import com.example.tplink.manager.ui.others.AboutActivity
import com.example.tplink.manager.util.PrefManager
import com.example.tplink.manager.util.makeToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Created by bggRGjQaUbCoE on 2024/5/23
 */
class SettingsPreferenceFragment : PreferenceFragmentCompat() {

    private val viewModel by viewModels<SettingsViewModel>(ownerProducer = { requireParentFragment() })

    class SettingsPreferenceDataStore : PreferenceDataStore() {
        override fun getString(key: String?, defValue: String?): String {
            return when (key) {
                else -> throw IllegalArgumentException("Invalid key: $key")
            }
        }

        override fun putString(key: String?, value: String?) {
            when (key) {

                else -> throw IllegalArgumentException("Invalid key: $key")
            }
        }

        override fun getBoolean(key: String?, defValue: Boolean): Boolean {
            return when (key) {
                "autoLogin" -> PrefManager.autoLogin
                else -> throw IllegalArgumentException("Invalid key: $key")
            }
        }

        override fun putBoolean(key: String?, value: Boolean) {
            when (key) {
                "autoLogin" -> PrefManager.autoLogin = value
                else -> throw IllegalArgumentException("Invalid key: $key")
            }
        }
    }


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = SettingsPreferenceDataStore()
        setPreferencesFromResource(R.xml.settings, rootKey)

        findPreference<Preference>("changePwd")?.setOnPreferenceClickListener {
            val currentPwd = PrefManager.password
            val editText = EditText(requireContext())
            editText.hint = currentPwd
            MaterialAlertDialogBuilder(requireContext()).apply {
                setTitle(getString(R.string.change_password))
                setView(editText)
                setNegativeButton(android.R.string.cancel, null)
                setPositiveButton(android.R.string.ok) { _, _ ->
                    with(editText.text.toString()) {
                        if (this == currentPwd)
                            requireContext().makeToast("same password")
                        else
                            viewModel.changePwd(currentPwd, this)
                    }
                }
            }.create().apply {
                window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
                editText.requestFocus()
            }.show()
            true
        }


        findPreference<Preference>("reboot")?.setOnPreferenceClickListener {
            MaterialAlertDialogBuilder(requireContext()).apply {
                setTitle(getString(R.string.reboot))
                setMessage("Reboot the router?")
                setNegativeButton(android.R.string.cancel, null)
                setPositiveButton(android.R.string.ok) { _, _ ->
                    viewModel.reboot()
                }
                show()
            }
            true
        }

        findPreference<Preference>("about")?.apply {
            summary = "${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})"
            setOnPreferenceClickListener {
                requireContext().startActivity(Intent(requireContext(), AboutActivity::class.java))
                true
            }
        }

    }

}