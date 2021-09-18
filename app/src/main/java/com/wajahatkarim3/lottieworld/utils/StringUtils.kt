package com.wajahatkarim3.lottieworld.utils

import android.content.Context
import com.wajahatkarim3.lottieworld.R

class StringUtils(val appContext: Context) {
    fun noNetworkErrorMessage() = appContext.getString(R.string.message_no_network_connected_str)
    fun somethingWentWrong() = appContext.getString(R.string.something_went_wrong_str)

    fun getString(stringId: Int) = appContext.getString(stringId)
}