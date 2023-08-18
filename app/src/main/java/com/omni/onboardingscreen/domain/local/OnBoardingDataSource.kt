package com.omni.onboardingscreen.domain.local

import kotlinx.coroutines.flow.Flow

interface OnBoardingDataSource {
    suspend fun isFirstTimeLaunch(isFirstLaunch: Boolean)
    fun getIsFirstTimeLaunch(): Flow<Boolean>
}