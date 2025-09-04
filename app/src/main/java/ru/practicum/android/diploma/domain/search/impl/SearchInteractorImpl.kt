package ru.practicum.android.diploma.domain.search.impl

import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.SearchRepository
import ru.practicum.android.diploma.domain.search.mapper.VacancyPreviewMapper
import ru.practicum.android.diploma.domain.search.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.search.models.VacancyPreviewPresent
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatus

class SearchInteractorImpl(
    private val repository: SearchRepository,
    private val mapper: VacancyPreviewMapper,
): SearchInteractor {

    override suspend fun searchVacancies(
        expression: String,
        page: Int
    ): Resource<VacanciesSearchResult<VacancyPreviewPresent>> {
        val result = repository.searchVacancies(expression, page)
        return when (result) {
            is Resource.Success -> {
                val presentItems = result.data?.items?.map { domainItem ->
                    mapper.mapToPresentation(domainItem)
                } ?: emptyList()

                Resource.Success(
                    VacanciesSearchResult(
                        found = result.data?.found ?: 0,
                        pages = result.data?.pages ?: 0,
                        page = result.data?.page ?: 0,
                        items = presentItems
                    )
                )
            }
            is Resource.Error -> {
                Resource.Error(result.message ?: ResponseStatus.UNKNOWN_ERROR)
            }
        }
    }
}
