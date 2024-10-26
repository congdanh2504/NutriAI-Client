package com.project.nutriai

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.gsonpref.gson
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NutriApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        Kotpref.init(this)
        Kotpref.gson = Gson()
    }

    companion object {
        lateinit var instance: NutriApplication
            private set
    }
}