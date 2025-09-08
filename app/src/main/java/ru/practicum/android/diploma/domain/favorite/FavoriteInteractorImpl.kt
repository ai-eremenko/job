package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.mappers.FavoriteVacancyMapper
import ru.practicum.android.diploma.domain.search.models.VacancyPreviewPresent
import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent

class FavoriteInteractorImpl(
    private val repository: FavoriteRepository,
    private val mapper: FavoriteVacancyMapper
) : FavoriteInteractor {

    override suspend fun toggleFavorite(vacancy: VacancyPresent) {
        val existingVacancy = repository.getVacancyById(vacancy.id)
        if (existingVacancy != null) {
            repository.removeFromFavorite(vacancy.id)
        } else {
            repository.addToFavorite(vacancy)
        }
    }

    override fun getFavorite(): Flow<List<VacancyPreviewPresent>> {
        return repository.getFavorite().map { vacancies ->
            mapper.mapToPreviewList(vacancies)
        }
    }

    override suspend fun getVacancyById(id: String): VacancyPresent? {
        return repository.getVacancyById(id)
    }
}
