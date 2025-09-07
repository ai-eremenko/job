package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent

class FavoriteInteractorImpl(
    private val repository: FavoriteRepository,
) : FavoriteInteractor {

    override suspend fun toggleFavorite(vacancy: VacancyPresent) {
        repository.toggleFavorite(vacancy)
    }

    override fun getFavorite(): Flow<List<VacancyPresent>> {
        return repository.getFavorite()
    }

    override suspend fun getVacancyById(id: String): VacancyPresent? {
        return repository.getVacancyById(id)
    }
}
