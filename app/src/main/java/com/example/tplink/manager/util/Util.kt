package com.example.tplink.manager.util

import android.content.Context
import android.widget.Toast

/**
 * Created by bggRGjQaUbCoE on 2024/5/22
 */
fun Context.makeToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}