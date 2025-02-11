package com.project.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.data.source.local.model.MealDetailEntity

@Database(entities = [MealDetailEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDetailDao(): MealDetailDao
}