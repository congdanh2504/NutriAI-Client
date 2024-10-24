package com.project.nutriai

import android.app.Application
import com.chibatching.kotpref.Kotpref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NutriApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        Kotpref.init(this)
    }

    companion object {
        lateinit var instance: NutriApplication
            private set
    }
}