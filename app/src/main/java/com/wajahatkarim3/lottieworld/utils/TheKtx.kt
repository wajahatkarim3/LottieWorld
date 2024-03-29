package com.wajahatkarim3.lottieworld.utils

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}