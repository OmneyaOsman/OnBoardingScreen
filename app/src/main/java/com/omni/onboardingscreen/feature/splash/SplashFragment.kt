package com.omni.onboardingscreen.feature.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.omni.onboardingscreen.R
import com.omni.onboardingscreen.domain.OnBoardingPrefManager
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashFragment : Fragment(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_splash, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            delay(3000)
            navigateToNext()
        }
    }

    private fun navigateToNext() {
        val isFirstTimeLaunch = OnBoardingPrefManager(requireNotNull(context)).isFirstTimeLaunch
        if (isFirstTimeLaunch)
            findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
        else
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}