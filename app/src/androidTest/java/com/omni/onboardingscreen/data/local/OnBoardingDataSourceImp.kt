package com.omni.onboardingscreen.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.omni.onboardingscreen.data.OnBoardingDataSourceImp
import com.omni.onboardingscreen.domain.local.OnBoardingDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


private const val TEST_DATASTORE_NAME: String = "test_datastore"

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class OnBoardingDataSourceImpTest {

    private lateinit var dataStore: DataStore<Preferences>
    private val repository: OnBoardingDataSource by lazy {
        OnBoardingDataSourceImp(
            dataStore
        )
    }

    @Before
    fun setup() {
        dataStore = PreferenceDataStoreFactory.create{
            ApplicationProvider.getApplicationContext<Context>()
                .preferencesDataStoreFile(name = TEST_DATASTORE_NAME)

        }
    }

    @Test
    fun whenSetIsFirstTimeLaunchToTrueThenGetIsFirstTimeLaunchValueChanged() = runTest {
        val isFirstTime = true
        repository.isFirstTimeLaunch(isFirstLaunch = isFirstTime)
        val result = repository.getIsFirstTimeLaunch().first()
        assertEquals(isFirstTime, result) // ✅ Passes
    }

    @After
    fun cleanup() {
        // Clear the DataStore after each test.
        runTest {
            dataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }
}