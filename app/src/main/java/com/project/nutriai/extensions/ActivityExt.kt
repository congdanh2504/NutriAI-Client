package com.project.nutriai.extensions

import android.app.Activity
import android.content.Intent

inline fun <reified T> Activity.startActivity(finish: Boolean = false) {
    startActivity(Intent(this, T::class.java))
    if (finish) finish()
}