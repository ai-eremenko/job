package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy

interface FavoriteRepository {
    suspend fun addToFavorite(vacancy: Vacancy)
    suspend fun removeFromFavorite(vacancy: Vacancy)
    fun getFavorite(): Flow<List<Vacancy>>
}

