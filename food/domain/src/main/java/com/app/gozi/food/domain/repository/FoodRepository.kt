package com.app.gozi.food.domain.repository

import com.app.gozi.food.domain.model.FoodItem
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getAllFoodItems(): Flow<List<FoodItem>>
    suspend fun getFoodItemById(id: String): FoodItem?
    fun searchFoodItems(query: String): Flow<List<FoodItem>>
    fun getFoodItemsByCategory(category: String): Flow<List<FoodItem>>
    suspend fun addFoodItem(foodItem: FoodItem)
    suspend fun updateFoodItem(foodItem: FoodItem)
    suspend fun deleteFoodItem(id: String)
}
