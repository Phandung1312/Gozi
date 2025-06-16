package com.app.gozi.sport.data.repository

import com.app.gozi.sport.domain.model.SportItem
import com.app.gozi.sport.domain.repository.SportRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteSportRepositoryImpl @Inject constructor(
    // TODO: Inject API service when available
    // private val sportApiService: SportApiService
) : SportRepository {
    
    override fun getAllSportItems(): Flow<List<SportItem>> = flow {
        // TODO: Replace with actual API call
        delay(1000)
        emit(emptyList<SportItem>())
    }
    
    override fun searchSportItems(query: String): Flow<List<SportItem>> = flow {
        // TODO: Replace with actual API call
        delay(800)
        emit(emptyList<SportItem>())
    }
    
    override fun getSportItemById(id: String): Flow<SportItem?> = flow {
        // TODO: Replace with actual API call
        delay(600)
        emit(null)
    }
    
    override fun getSportItemsByCategory(category: String): Flow<List<SportItem>> = flow {
        // TODO: Replace with actual API call
        delay(800)
        emit(emptyList<SportItem>())
    }
    
    override fun getSportItemsByDifficulty(difficulty: String): Flow<List<SportItem>> = flow {
        // TODO: Replace with actual API call
        delay(800)
        emit(emptyList<SportItem>())
    }
}
