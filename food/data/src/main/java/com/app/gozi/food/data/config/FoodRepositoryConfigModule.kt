package com.app.gozi.food.data.config

import com.app.gozi.food.data.di.MockFoodRepository
import com.app.gozi.food.data.di.RemoteFoodRepository
import com.app.gozi.food.domain.repository.FoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FoodRepositoryConfigModule {
    
    // Configuration flag - change this to switch between mock and real data
    private const val USE_MOCK_DATA = true
    
    @Provides
    @Singleton
    fun provideFoodRepository(
        @MockFoodRepository mockRepository: FoodRepository,
        @RemoteFoodRepository remoteRepository: FoodRepository
    ): FoodRepository {
        return if (USE_MOCK_DATA) {
            mockRepository
        } else {
            remoteRepository
        }
    }
}
