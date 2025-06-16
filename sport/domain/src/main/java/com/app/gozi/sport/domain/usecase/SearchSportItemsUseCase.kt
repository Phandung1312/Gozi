package com.app.gozi.sport.domain.usecase

import com.app.gozi.sport.domain.model.SportItem
import com.app.gozi.sport.domain.repository.SportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchSportItemsUseCase @Inject constructor(
    private val repository: SportRepository
) {
    operator fun invoke(query: String): Flow<List<SportItem>> {
        return repository.searchSportItems(query)
    }
}
