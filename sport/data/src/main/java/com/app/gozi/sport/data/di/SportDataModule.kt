package com.app.gozi.sport.data.di

import com.app.gozi.sport.data.repository.MockSportRepositoryImpl
import com.app.gozi.sport.data.repository.RemoteSportRepositoryImpl
import com.app.gozi.sport.domain.repository.SportRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MockSportRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteSportRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class SportDataModule {
    
    @Binds
    @Singleton
    @MockSportRepository
    abstract fun bindMockSportRepository(
        mockSportRepositoryImpl: MockSportRepositoryImpl
    ): SportRepository
    
    @Binds
    @Singleton
    @RemoteSportRepository
    abstract fun bindRemoteSportRepository(
        remoteSportRepositoryImpl: RemoteSportRepositoryImpl
    ): SportRepository
}
