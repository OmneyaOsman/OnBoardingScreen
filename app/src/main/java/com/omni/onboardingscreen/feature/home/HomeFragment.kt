package com.omni.onboardingscreen.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.omni.onboardingscreen.R
import com.omni.onboardingscreen.databinding.FragmentHomeBinding
import com.omni.onboardingscreen.feature.changeStatusBarColor

class HomeFragment :Fragment(){
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeStatusBarColor(R.color.colorWhite)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}