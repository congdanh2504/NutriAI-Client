package com.project.data.di

import android.content.Context
import androidx.room.Room
import com.project.data.source.local.AppDatabase
import com.project.data.source.local.MealDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "meal_database"
        ).build()
    }

    @Provides
    fun provideMealDetailDao(database: AppDatabase): MealDetailDao {
        return database.mealDetailDao()
    }
}