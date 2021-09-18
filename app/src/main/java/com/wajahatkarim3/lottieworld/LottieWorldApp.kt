package com.wajahatkarim3.lottieworld

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class LottieWorldApp: Application() {

    override fun onCreate() {
        super.onCreate()
        setupDayNightMode()
    }

    private fun setupDayNightMode() {
        // Get UI mode and set
        val mode = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }

    /**
     * Returns [Boolean] based on current time.
     * Returns true if hours are between 06:00 pm - 07:00 am
     */
    private fun isNight(): Boolean {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return (currentHour <= 7 || currentHour >= 18)
    }
}