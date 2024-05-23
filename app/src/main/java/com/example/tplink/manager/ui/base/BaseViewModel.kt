package com.example.tplink.manager.ui.base

import androidx.lifecycle.ViewModel
import com.example.tplink.manager.util.PrefManager
import java.net.URLDecoder

/**
 * Created by bggRGjQaUbCoE on 2024/5/23
 */
open class BaseViewModel : ViewModel() {

    var isInit = true

    val stok: String by lazy { URLDecoder.decode(PrefManager.stok, "UTF-8") }

}