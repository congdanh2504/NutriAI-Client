package com.project.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.data.source.local.model.MealDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealDetail(mealDetail: MealDetailEntity)

    @Query("DELETE FROM meal_detail WHERE id = :id")
    suspend fun removeMealDetail(id: String)

    @Query("SELECT * FROM meal_detail WHERE userId = :userId")
    fun getMealDetailByUserId(userId: String): Flow<List<MealDetailEntity>>

    @Query("SELECT * FROM meal_detail WHERE id = :id")
    fun getMealDetailById(id: String): Flow<MealDetailEntity?>
}