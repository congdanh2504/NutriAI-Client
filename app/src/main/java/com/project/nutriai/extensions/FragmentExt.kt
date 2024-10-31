package com.project.nutriai.extensions

import android.content.Intent
import androidx.fragment.app.Fragment

inline fun <reified T> Fragment.startActivity(finish: Boolean = false) {
    startActivity(Intent(requireActivity(), T::class.java))
    if (finish) requireActivity().finish()
}