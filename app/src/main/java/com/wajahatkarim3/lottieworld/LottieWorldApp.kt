package com.wajahatkarim3.lottieworld

import android.app.Application
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatDelegate
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.util.CoilUtils
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import java.util.*

@HiltAndroidApp
class LottieWorldApp: Application(), ImageLoaderFactory {

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

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .componentRegistry {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder(applicationContext))
                } else {
                    add(GifDecoder())
                }
            }
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(applicationContext))
                    .build()
            }
            .build()
    }
}