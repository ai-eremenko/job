package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy

interface FavoriteInteractor {
    suspend fun toggleFavorite(vacancy: Vacancy)
    fun getFavorite(): Flow<List<Vacancy>>
    suspend fun removeFromFavorite(vacancy: Vacancy)
}
