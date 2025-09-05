package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.search.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.search.models.VacancyPreview
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {
    fun searchVacancies(expression: String, page: Int): Flow<Resource<VacanciesSearchResult<VacancyPreview>>>
}
