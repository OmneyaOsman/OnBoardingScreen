package com.omni.onboardingscreen.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omni.onboardingscreen.R
import com.omni.onboardingscreen.databinding.ActivityMainBinding
import com.omni.onboardingscreen.databinding.OnboardingActivityBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      val  binding = OnboardingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}
