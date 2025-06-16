package com.app.gozi.food.domain.usecase

import com.app.gozi.food.domain.model.FoodItem
import com.app.gozi.food.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchFoodItemsUseCase @Inject constructor(
    private val repository: FoodRepository
) {
    operator fun invoke(query: String): Flow<List<FoodItem>> {
        return repository.searchFoodItems(query)
    }
}
