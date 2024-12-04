package com.project.data.di

import com.project.data.repository.AuthRepositoryImpl
import com.project.data.repository.FruitIdentificationRepositoryImpl
import com.project.data.repository.LocalRepositoryImpl
import com.project.data.repository.MealRepositoryImpl
import com.project.data.repository.UserRepositoryImpl
import com.project.domain.repository.AuthRepository
import com.project.domain.repository.FruitIdentificationRepository
import com.project.domain.repository.LocalRepository
import com.project.domain.repository.MealRepository
import com.project.domain.repository.UserRepository
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
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindMealRepository(impl: MealRepositoryImpl): MealRepository

    @Binds
    abstract fun bindLocalRepository(impl: LocalRepositoryImpl): LocalRepository

    @Binds
    abstract fun bindFruitIdentificationRepository(impl: FruitIdentificationRepositoryImpl): FruitIdentificationRepository
}