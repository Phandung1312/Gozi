package com.app.gozi.sport.domain.usecase

import com.app.gozi.sport.domain.model.SportItem
import com.app.gozi.sport.domain.repository.SportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSportItemsUseCase @Inject constructor(
    private val repository: SportRepository
) {
    operator fun invoke(): Flow<List<SportItem>> {
        return repository.getAllSportItems()
    }
}
