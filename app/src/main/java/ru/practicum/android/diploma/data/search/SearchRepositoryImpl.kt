package ru.practicum.android.diploma.data.search

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.RequestDto
import ru.practicum.android.diploma.data.dto.responses.VacanciesResponse
import ru.practicum.android.diploma.data.mappers.VacancyMapper
import ru.practicum.android.diploma.domain.search.SearchRepository
import ru.practicum.android.diploma.domain.search.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.search.models.VacancyPreview
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatus

class SearchRepositoryImpl(
    private val networkClient: NetworkClient
): SearchRepository {

    override suspend fun searchVacancies(
        expression: String,
        page: Int
    ): Resource<VacanciesSearchResult<VacancyPreview>> {
        val response = networkClient.doRequest(RequestDto.VacanciesRequest(expression, page))

        return when (response.status) {
            ResponseStatus.SUCCESS -> {
                val vacanciesResponse = response as? VacanciesResponse
                if (vacanciesResponse != null) {
                    val data = VacancyMapper.mapToDomain(vacanciesResponse)
                    Resource.Success(data)
                } else {
                    Resource.Error(ResponseStatus.UNKNOWN_ERROR)
                }
            }
            else -> Resource.Error(response.status)
        }
    }
}
