package ru.practicum.android.diploma.domain.vacancy.impl

import ru.practicum.android.diploma.domain.favorite.FavoriteRepository
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.vacancy.VacancyRepository
import ru.practicum.android.diploma.domain.vacancy.mappers.VacancyMapper
import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatus

class VacancyInteractorImpl(
    private val repository: VacancyRepository,
    private val favoriteRepository: FavoriteRepository,
    private val mapper: VacancyMapper
) : VacancyInteractor {

    override suspend fun getVacancyById(id: String): Resource<VacancyPresent> {
        val result = repository.getVacancyById(id)
        return when (result) {
            is Resource.Success -> {
                if (result.data != null) {
                    val data = mapper.mapToPresentation(result.data)
                    if (isFavorite(data.id)) {
                        val dataFavorite = data.copy(isFavorite = true)
                        favoriteRepository.addToFavorite(dataFavorite)
                        Resource.Success(dataFavorite)
                    } else Resource.Success(data)
                } else {
                    Resource.Error(ResponseStatus.UNKNOWN_ERROR)
                }
            }

            is Resource.Error -> {
                Resource.Error(result.message ?: ResponseStatus.UNKNOWN_ERROR)
            }
        }
    }

    private suspend fun isFavorite(id: String): Boolean {
        return favoriteRepository.getVacancyById(id) != null
    }
}
