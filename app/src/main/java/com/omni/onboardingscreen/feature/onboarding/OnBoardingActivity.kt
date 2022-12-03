package com.omni.onboardingscreen.feature.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omni.onboardingscreen.R
import com.omni.onboardingscreen.databinding.ActivityMainBinding

class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      val  binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}
