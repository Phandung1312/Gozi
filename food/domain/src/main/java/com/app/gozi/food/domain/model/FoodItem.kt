package com.app.gozi.food.domain.model

data class FoodItem(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String? = null,
    val category: String,
    val price: Double? = null,
    val rating: Float? = null,
    val preparationTime: Int? = null, // in minutes
    val ingredients: List<String> = emptyList(),
    val isVegetarian: Boolean = false,
    val isSpicy: Boolean = false
)
