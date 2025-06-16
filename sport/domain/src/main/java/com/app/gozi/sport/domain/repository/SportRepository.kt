package com.app.gozi.sport.domain.repository

import com.app.gozi.sport.domain.model.SportItem
import kotlinx.coroutines.flow.Flow

interface SportRepository {
    fun getAllSportItems(): Flow<List<SportItem>>
    fun searchSportItems(query: String): Flow<List<SportItem>>
    fun getSportItemById(id: String): Flow<SportItem?>
    fun getSportItemsByCategory(category: String): Flow<List<SportItem>>
    fun getSportItemsByDifficulty(difficulty: String): Flow<List<SportItem>>
}
