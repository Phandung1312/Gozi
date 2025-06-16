package com.app.gozi.sport.data.repository

import com.app.gozi.sport.data.local.MockSportData
import com.app.gozi.sport.domain.model.SportItem
import com.app.gozi.sport.domain.repository.SportRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockSportRepositoryImpl @Inject constructor() : SportRepository {
    
    override fun getAllSportItems(): Flow<List<SportItem>> = flow {
        delay(500)
        emit(MockSportData.mockSportItems)
    }
    
    override fun searchSportItems(query: String): Flow<List<SportItem>> = flow {
        delay(300)
        val filteredItems = MockSportData.mockSportItems.filter { sportItem ->
            sportItem.name.contains(query, ignoreCase = true) ||
            sportItem.description.contains(query, ignoreCase = true) ||
            sportItem.category.contains(query, ignoreCase = true) ||
            sportItem.equipment.any { it.contains(query, ignoreCase = true) } ||
            sportItem.benefits.any { it.contains(query, ignoreCase = true) }
        }
        emit(filteredItems)
    }
    
    override fun getSportItemById(id: String): Flow<SportItem?> = flow {
        delay(200)
        val sportItem = MockSportData.mockSportItems.find { it.id == id }
        emit(sportItem)
    }
    
    override fun getSportItemsByCategory(category: String): Flow<List<SportItem>> = flow {
        delay(300)
        val filteredItems = MockSportData.mockSportItems.filter { 
            it.category.equals(category, ignoreCase = true) 
        }
        emit(filteredItems)
    }
    
    override fun getSportItemsByDifficulty(difficulty: String): Flow<List<SportItem>> = flow {
        delay(300)
        val filteredItems = MockSportData.mockSportItems.filter { 
            it.difficulty.equals(difficulty, ignoreCase = true) 
        }
        emit(filteredItems)
    }
}
