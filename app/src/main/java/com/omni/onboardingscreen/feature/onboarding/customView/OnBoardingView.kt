package com.omni.onboardingscreen.feature.onboarding.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.omni.onboardingscreen.core.pageCompositePageTransformer
import com.omni.onboardingscreen.core.setParallaxTransformation
import com.omni.onboardingscreen.databinding.OnboardingViewBinding
import com.omni.onboardingscreen.domain.OnBoardingPrefManager
import com.omni.onboardingscreen.feature.onboarding.OnBoardingPagerAdapter
import com.omni.onboardingscreen.feature.onboarding.entity.OnBoardingPage

class OnBoardingView @JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val numberOfPages by lazy { OnBoardingPage.values().size }
    private val prefManager: OnBoardingPrefManager


    init {
        val binding = OnboardingViewBinding.inflate(LayoutInflater.from(context), this, true)
        with(binding) {
            setUpSlider()
            addingButtonsClickListeners()
            prefManager = OnBoardingPrefManager(root.context)
        }

    }

    private fun OnboardingViewBinding.setUpSlider() {
        with(slider) {
            adapter = OnBoardingPagerAdapter()

            setPageTransformer { page, position ->
                setParallaxTransformation(page, position)
            }
//
//            setPageTransformer(pageCompositePageTransformer)

            addSlideChangeListener()

            val wormDotsIndicator = pageIndicator
            wormDotsIndicator.attachTo(this)
        }
    }


    private fun OnboardingViewBinding.addSlideChangeListener() {

        slider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (numberOfPages > 1) {
                    val newProgress = (position + positionOffset) / (numberOfPages - 1)
                    onboardingRoot.progress = newProgress
                }
            }
        })
    }

    private fun OnboardingViewBinding.addingButtonsClickListeners() {
        nextBtn.setOnClickListener { navigateToNextSlide(slider) }
        skipBtn.setOnClickListener {
            setFirstTimeLaunchToFalse()
            navigateToHomeScreen()
        }
        startBtn.setOnClickListener {
            setFirstTimeLaunchToFalse()
            navigateToHomeScreen()
        }
    }

    private fun setFirstTimeLaunchToFalse() {
        prefManager.isFirstTimeLaunch = false
    }

    private fun navigateToNextSlide(slider: ViewPager2?) {
        val nextSlidePos: Int = slider?.currentItem?.plus(1) ?: 0
        slider?.setCurrentItem(nextSlidePos, true)
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment , null)
    }
}