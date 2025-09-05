package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.domain.favorite.FavoriteRepository
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy

class FavoriteInteractorImpl(
    private val repository: FavoriteRepository
) : FavoriteInteractor {

    override suspend fun toggleFavorite(vacancy: Vacancy) {
        if (vacancy.isFavorite) {
            repository.removeFromFavorite(vacancy)
        } else {
            repository.addToFavorite(vacancy)
        }
    }

    override fun getFavorite(): Flow<List<Vacancy>> {
        return repository.getFavorite()
    }

    override suspend fun removeFromFavorite(vacancy: Vacancy) {
        repository.removeFromFavorite(vacancy)
    }

    override suspend fun getVacancyById(id: String): Vacancy? {
        return repository.getVacancyById(id)
    }
}
