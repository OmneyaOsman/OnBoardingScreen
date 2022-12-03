package com.omni.onboardingscreen.feature.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.omni.onboardingscreen.R
import com.omni.onboardingscreen.databinding.ActivityMainBinding
import com.omni.onboardingscreen.databinding.OnboardingPageItemBinding
import com.omni.onboardingscreen.feature.onboarding.PagerViewHolder.Companion.LAYOUT
import com.omni.onboardingscreen.feature.onboarding.entity.OnBoardingPage

class OnBoardingPagerAdapter(private val onBoardingPageList: Array<OnBoardingPage> = OnBoardingPage.values()) :
    RecyclerView.Adapter<PagerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PagerViewHolder =
        LayoutInflater.from(parent.context).let {
            OnboardingPageItemBinding.inflate(
                it, parent, false
            ).let { binding -> PagerViewHolder(binding) }
        }


    override fun getItemCount() = onBoardingPageList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(onBoardingPageList[position])
    }
}

class PagerViewHolder(private val binding: OnboardingPageItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(onBoardingPage: OnBoardingPage) {
        val res = binding.root.context.resources
        binding.titleTv.text = res.getString(onBoardingPage.titleResource)
        binding.subTitleTv.text = res.getString(onBoardingPage.subTitleResource)
        binding.descTV.text = res.getString(onBoardingPage.descriptionResource)
        binding.img.setImageResource(onBoardingPage.logoResource)
    }

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.onboarding_page_item
    }
}