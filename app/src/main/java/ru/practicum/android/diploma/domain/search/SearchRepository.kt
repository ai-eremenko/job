package ru.practicum.android.diploma.domain.search

import ru.practicum.android.diploma.domain.search.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.search.models.VacancyPreview
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {
    suspend fun searchVacancies(expression: String, page: Int): Resource<VacanciesSearchResult<VacancyPreview>>
}
