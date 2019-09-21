package com.omni.onboardingscreen.domain

import android.content.Context
import android.content.SharedPreferences

class OnBoardingPrefManager (_context: Context) {


    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor


    var isFirstTimeLaunch: Boolean
        get() {
            return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
        }
        set(isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
            editor.commit()
        }


    init {
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    companion object {
        private const val IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH"
        private const val PREF_NAME = "PREF_NAME"
    }

}