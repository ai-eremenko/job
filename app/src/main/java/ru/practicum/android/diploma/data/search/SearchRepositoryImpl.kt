package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.RequestDto
import ru.practicum.android.diploma.data.dto.responses.VacanciesResponse
import ru.practicum.android.diploma.data.mappers.VacancyMapper
import ru.practicum.android.diploma.domain.filteringsettings.FilterRepository
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings
import ru.practicum.android.diploma.domain.search.SearchRepository
import ru.practicum.android.diploma.domain.search.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.search.models.VacancyPreview
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatus

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val filter: FilterRepository
) : SearchRepository {

    override fun searchVacancies(
        expression: String,
        page: Int
    ): Flow<Resource<VacanciesSearchResult<VacancyPreview>>> = flow {
        val filterOptions = filter.getFilterOptions()
        val requestOptions = buildRequestOptions(filterOptions)

        val response = networkClient.doRequest(
            RequestDto.VacanciesRequest(
                expression,
                page,
                requestOptions,
                filterOptions.onlyWithSalary
            )
        )

        emit(processResponse(response))
    }

    private fun buildRequestOptions(filter: FilterSettings): Map<String, Int> {
        return buildMap {
            if (filter.areaId != null) {
                put("area", filter.areaId)
            }
            if (filter.areaId == null && filter.countryId != null) {
                put("area", filter.countryId)
            }
            if (filter.industryId != null) {
                put("industry", filter.industryId)
            }
            if (filter.salary != null) {
                put("salary", filter.salary)
            }
        }
    }

    private fun processResponse(response: Any): Resource<VacanciesSearchResult<VacancyPreview>> {
        return when (val status = (response as? ru.practicum.android.diploma.data.dto.responses.Response)?.status) {
            ResponseStatus.SUCCESS -> {
                val vacanciesResponse = response as? VacanciesResponse
                vacanciesResponse?.let {
                    Resource.Success(VacancyMapper.mapToDomain(it))
                } ?: Resource.Error(ResponseStatus.UNKNOWN_ERROR)
            }

            else -> Resource.Error(status ?: ResponseStatus.UNKNOWN_ERROR)
        }
    }

}
