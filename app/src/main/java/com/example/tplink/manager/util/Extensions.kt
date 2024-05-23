package com.example.tplink.manager.util

import android.content.res.Resources
import android.util.TypedValue

val Number.dp get() = (toFloat() * Resources.getSystem().displayMetrics.density).toInt()

val Number.sp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )