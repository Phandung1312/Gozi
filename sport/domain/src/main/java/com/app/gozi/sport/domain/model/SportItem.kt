package com.app.gozi.sport.domain.model

data class SportItem(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String? = null,
    val category: String,
    val difficulty: String, // "Dễ", "Trung bình", "Khó"
    val duration: Int, // minutes
    val calories: Int, // calories burned per session
    val equipment: List<String> = emptyList(),
    val benefits: List<String> = emptyList(),
    val isIndoor: Boolean = true,
    val isTeamSport: Boolean = false,
    val rating: Float = 0.0f
)
