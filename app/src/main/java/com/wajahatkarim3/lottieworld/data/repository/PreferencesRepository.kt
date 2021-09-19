package com.wajahatkarim3.lottieworld.data.repository

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext

interface PreferencesRepository {
    fun isUserLoggedIn(): Boolean
    fun setUserLogin(value: Boolean)
}

class DefaultPreferencesRepository(
    @ApplicationContext val context: Context
): PreferencesRepository {

    override fun isUserLoggedIn(): Boolean {
        return getSharedPrefs().getBoolean(PreferencesKeys.PREF_KEY_USER_LOGIN_STATUS, false)
    }

    override fun setUserLogin(value: Boolean) {
        getSharedPrefs().edit().putBoolean(PreferencesKeys.PREF_KEY_USER_LOGIN_STATUS, value).commit()
    }

    fun getSharedPrefs(): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }
}

object PreferencesKeys {
    const val PREF_KEY_USER_LOGIN_STATUS = "PREF_KEY_USER_LOGIN_STATUS"
}