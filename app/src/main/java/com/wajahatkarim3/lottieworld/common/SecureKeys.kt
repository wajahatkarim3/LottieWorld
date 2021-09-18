package com.wajahatkarim3.lottieworld.common

object SecureKeys {
    init {
        System.loadLibrary("native-lib")
    }

    external fun baseUrl(): String
}