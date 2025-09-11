package ru.practicum.android.diploma.domain.search.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
) : SearchInteractor {

    override fun searchVacancies(
        expression: String,
        page: Int
    ): Flow<Resource<VacanciesSearchResult<VacancyPreviewPresent>>> {
        return repository.searchVacancies(expression, page)
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val presentItems = resource.data?.items?.map { domainItem ->
                            mapper.mapToPresentation(domainItem)
                        } ?: emptyList()

                        Resource.Success(
                            VacanciesSearchResult(
                                found = resource.data?.found ?: 0,
                                pages = resource.data?.pages ?: 0,
                                page = resource.data?.page ?: 0,
                                items = presentItems
                            )
                        )
                    }

                    is Resource.Error -> {
                        Resource.Error(resource.message ?: ResponseStatus.UNKNOWN_ERROR)
                    }
                }
            }
    }
}
