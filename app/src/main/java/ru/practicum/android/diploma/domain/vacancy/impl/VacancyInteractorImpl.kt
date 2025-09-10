package ru.practicum.android.diploma.domain.vacancy.impl

import ru.practicum.android.diploma.domain.favorite.FavoriteRepository
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.vacancy.VacancyRepository
import ru.practicum.android.diploma.domain.vacancy.mappers.VacancyMapper
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatus

class VacancyInteractorImpl(
    private val repository: VacancyRepository,
    private val favoriteRepository: FavoriteRepository,
    private val mapper: VacancyMapper
) : VacancyInteractor {

    override suspend fun getVacancyById(id: String): Resource<VacancyPresent> {
        return when (val result = repository.getVacancyById(id)) {
            is Resource.Success -> processSuccessfulResponse(result.data)
            is Resource.Error -> processErrorResponse(result.message, id)
        }
    }

    private suspend fun processSuccessfulResponse(result: Vacancy?): Resource<VacancyPresent> {
        return if (result != null) {
            val data = mapper.mapToPresentation(result)
            if (isFavorite(data.id)) {
                val dataFavorite = data.copy(isFavorite = true)
                favoriteRepository.addToFavorite(dataFavorite)
                Resource.Success(dataFavorite)
            } else {
                Resource.Success(data)
            }
        } else {
            Resource.Error(ResponseStatus.NOT_FOUND)
        }
    }

    private suspend fun processErrorResponse(
        message: ResponseStatus?,
        id: String
    ): Resource<VacancyPresent> {
        return when (message) {
            ResponseStatus.NOT_FOUND -> {
                removeFromFavoriteIfExists(id)
                Resource.Error(ResponseStatus.NOT_FOUND)
            }

            ResponseStatus.NO_INTERNET -> {
                loadFromFavorite(id)?.let { favoriteVacancy ->
                    Resource.Success(favoriteVacancy)
                } ?: Resource.Error(ResponseStatus.NO_INTERNET)
            }

            else -> {
                Resource.Error(message ?: ResponseStatus.UNKNOWN_ERROR)
            }
        }
    }

    private suspend fun removeFromFavoriteIfExists(id: String) {
        if (isFavorite(id)) {
            favoriteRepository.removeFromFavorite(id)
        }
    }

    private suspend fun loadFromFavorite(id: String): VacancyPresent? {
        return favoriteRepository.getVacancyById(id)
    }

    private suspend fun isFavorite(id: String): Boolean {
        return favoriteRepository.getVacancyById(id) != null
    }
}
