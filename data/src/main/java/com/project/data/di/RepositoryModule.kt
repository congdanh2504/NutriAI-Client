package com.project.data.di

import com.project.data.repository.AuthRepository
import com.project.data.repository.AuthRepositoryImpl
import com.project.data.repository.MealRepository
import com.project.data.repository.MealRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindMealRepository(impl: MealRepositoryImpl): MealRepository
}