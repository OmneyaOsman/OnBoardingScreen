package com.omni.onboardingscreen.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.omni.onboardingscreen.domain.local.OnBoardingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class OnBoardingDataSourceImp(private val dataStore: DataStore<Preferences>) :
    OnBoardingDataSource {

    override suspend fun isFirstTimeLaunch(isFirstLaunch: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_FIRST_LAUNCH_VALUE] =
                isFirstLaunch
        }

    }

    override fun getIsFirstTimeLaunch(): Flow<Boolean> =
        dataStore.data
            .catch { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    Log.e(
                        OnBoardingDataSourceImp::class.java.simpleName,
                        "Error reading selected_expiring_policy_day_value preferences. $exception"
                    )
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[PreferencesKeys.IS_FIRST_LAUNCH_VALUE]
                    ?: false
            }

    private object PreferencesKeys {
        val IS_FIRST_LAUNCH_VALUE =
            booleanPreferencesKey("isFirst_Launch")
    }
}