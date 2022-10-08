package com.smparkworld.parkdatetimepicker.extension

import androidx.fragment.app.Fragment

fun Fragment.getExtra(key: String, default: Int): Int {
    return arguments?.getInt(key, default) ?: default
}

fun Fragment.getExtra(key: String, default: String): String {
    return arguments?.getString(key, default) ?: default
}