package com.project.nutriai

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
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

        setupCoilCache()
    }

    private fun setupCoilCache() {
        val imageLoader = ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()

        Coil.setImageLoader(imageLoader)
    }

    companion object {
        lateinit var instance: NutriApplication
            private set
    }
}