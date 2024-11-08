package com.project.data.di

import com.project.data.repository.AuthRepositoryImpl
import com.project.data.repository.LocalRepositoryImpl
import com.project.data.repository.MealRepositoryImpl
import com.project.domain.repository.AuthRepository
import com.project.domain.repository.LocalRepository
import com.project.domain.repository.MealRepository
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

    @Binds
    abstract fun bindLocalRepository(impl: LocalRepositoryImpl): LocalRepository
}