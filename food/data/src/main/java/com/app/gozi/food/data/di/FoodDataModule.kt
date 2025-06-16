package com.app.gozi.food.data.di

import com.app.gozi.food.data.repository.MockFoodRepositoryImpl
import com.app.gozi.food.data.repository.RemoteFoodRepositoryImpl
import com.app.gozi.food.domain.repository.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MockFoodRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteFoodRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class FoodDataModule {
    
    @Binds
    @Singleton
    @MockFoodRepository
    abstract fun bindMockFoodRepository(
        mockFoodRepositoryImpl: MockFoodRepositoryImpl
    ): FoodRepository
    
    @Binds
    @Singleton
    @RemoteFoodRepository
    abstract fun bindRemoteFoodRepository(
        remoteFoodRepositoryImpl: RemoteFoodRepositoryImpl
    ): FoodRepository
}
