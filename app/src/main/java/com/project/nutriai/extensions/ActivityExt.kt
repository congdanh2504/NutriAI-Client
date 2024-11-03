package com.project.nutriai.extensions

import android.app.Activity
import android.content.Intent

inline fun <reified T> Activity.startActivity(finish: Boolean = false) {
    startActivity(Intent(this, T::class.java))
    if (finish) finish()
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}