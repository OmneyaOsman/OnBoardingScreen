package com.omni.onboardingscreen.feature

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.changeStatusBarColor(@ColorRes color: Int) {
    requireNotNull(activity).window.statusBarColor =
        ContextCompat.getColor(requireNotNull(context), color)
}