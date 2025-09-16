package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.RequestDto
import ru.practicum.android.diploma.data.dto.responses.VacanciesResponse
import ru.practicum.android.diploma.data.mappers.VacancyMapper
import ru.practicum.android.diploma.domain.filteringsettings.FilterRepository
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
        val options: HashMap<String, Int> = HashMap()
        val filter = filter.getFilterOptions()
        if (filter.areaId != null) {
            options["area"] = filter.areaId
        }
        if (filter.areaId == null && filter.countryId != null)
            options["area"] = filter.countryId
        if (filter.industryId != null) {
            options["industry"] = filter.industryId
        }
        if (filter.salary != null) {
            options["salary"] = filter.salary
        }
        val onlyWithSalary = filter.onlyWithSalary
        val response = networkClient.doRequest(RequestDto.VacanciesRequest(expression, page, options, onlyWithSalary))

        when (response.status) {
            ResponseStatus.SUCCESS -> {
                val vacanciesResponse = response as? VacanciesResponse
                if (vacanciesResponse != null) {
                    val data = VacancyMapper.mapToDomain(vacanciesResponse)
                    emit(Resource.Success(data))
                } else {
                    emit(Resource.Error(ResponseStatus.UNKNOWN_ERROR))
                }
            }

            else -> emit(Resource.Error(response.status))
        }
    }
}
