package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.search.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.search.models.VacancyPreviewPresent
import ru.practicum.android.diploma.util.Resource

interface SearchInteractor {
    fun searchVacancies(expression: String, page: Int): Flow<Resource<VacanciesSearchResult<VacancyPreviewPresent>>>
}
