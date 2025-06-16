package com.app.gozi.sport.data.config

import com.app.gozi.sport.data.di.MockSportRepository
import com.app.gozi.sport.data.di.RemoteSportRepository
import com.app.gozi.sport.domain.repository.SportRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SportRepositoryConfigModule {
    
    // Configuration flag - change this to switch between mock and real data
    private const val USE_MOCK_DATA = true
    
    @Provides
    @Singleton
    fun provideSportRepository(
        @MockSportRepository mockRepository: SportRepository,
        @RemoteSportRepository remoteRepository: SportRepository
    ): SportRepository {
        return if (USE_MOCK_DATA) {
            mockRepository
        } else {
            remoteRepository
        }
    }
}
