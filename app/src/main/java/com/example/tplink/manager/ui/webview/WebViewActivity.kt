package com.example.tplink.manager.ui.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import androidx.activity.OnBackPressedCallback
import com.example.tplink.manager.databinding.ActivityWebviewBinding
import com.example.tplink.manager.ui.base.BaseActivity

class WebViewActivity : BaseActivity<ActivityWebviewBinding>() {

    private val filePath by lazy { intent.getStringExtra("filePath") }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        filePath?.let {
            binding.webView.apply {
                webChromeClient = WebChromeClient()
                addJavascriptInterface(MyJsInterface(this@WebViewActivity), "Me")
                settings.javaScriptEnabled = true
                settings.allowFileAccessFromFileURLs = true
                settings.allowUniversalAccessFromFileURLs = true
                settings.domStorageEnabled = true;
                settings.allowContentAccess = true;
                settings.allowFileAccess = true;
                loadUrl("$it/index.html")
            }
        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.webView.canGoBack())
                binding.webView.goBack()
            else
                finish()
        }
    }

}