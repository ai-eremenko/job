package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent

interface FavoriteRepository {
    suspend fun toggleFavorite(vacancy: VacancyPresent)
    fun getFavorite(): Flow<List<VacancyPresent>>
    suspend fun getVacancyById(id: String): VacancyPresent?
}
