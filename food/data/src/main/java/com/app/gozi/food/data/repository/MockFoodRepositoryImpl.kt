package com.app.gozi.food.data.repository

import com.app.gozi.food.data.local.MockFoodData
import com.app.gozi.food.domain.model.FoodItem
import com.app.gozi.food.domain.repository.FoodRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockFoodRepositoryImpl @Inject constructor() : FoodRepository {
    
    override fun getAllFoodItems(): Flow<List<FoodItem>> = flow {
        // Simulate network delay
        delay(500)
        emit(MockFoodData.mockFoodItems)
    }
    
    override fun searchFoodItems(query: String): Flow<List<FoodItem>> = flow {
        // Simulate network delay
        delay(300)
        val filteredItems = MockFoodData.mockFoodItems.filter { foodItem ->
            foodItem.name.contains(query, ignoreCase = true) ||
            foodItem.description.contains(query, ignoreCase = true) ||
            foodItem.category.contains(query, ignoreCase = true) ||
            foodItem.ingredients.any { it.contains(query, ignoreCase = true) }
        }
        emit(filteredItems)
    }
    
    override suspend fun getFoodItemById(id: String): FoodItem? {
        // Simulate network delay
        delay(200)
        return MockFoodData.mockFoodItems.find { it.id == id }
    }
    
    override fun getFoodItemsByCategory(category: String): Flow<List<FoodItem>> = flow {
        // Simulate network delay
        delay(300)
        val filteredItems = MockFoodData.mockFoodItems.filter { 
            it.category.equals(category, ignoreCase = true) 
        }
        emit(filteredItems)
    }
    
    override suspend fun addFoodItem(foodItem: FoodItem) {
        // TODO: Implement adding food item to mock data
        delay(200)
    }
    
    override suspend fun updateFoodItem(foodItem: FoodItem) {
        // TODO: Implement updating food item in mock data
        delay(200)
    }
    
    override suspend fun deleteFoodItem(id: String) {
        // TODO: Implement deleting food item from mock data
        delay(200)
    }
}
