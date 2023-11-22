package com.omni.onboardingscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.omni.onboardingscreen.feature.onboarding.OnBoardingScreen
import com.omni.onboardingscreen.feature.onboarding.model.OnBoardingPage
import com.omni.onboardingscreen.theme.OnBoardingTheme

class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnBoardingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OnBoardingScreen(
                        modifier = Modifier.fillMaxWidth(),
                        OnBoardingPage.values().toMutableList()
                    )
                }
            }
        }
    }
}

