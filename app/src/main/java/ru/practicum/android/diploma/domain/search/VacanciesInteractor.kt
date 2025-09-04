package ru.practicum.android.diploma.domain.search

import ru.practicum.android.diploma.domain.search.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.search.models.VacancyPreviewPresent
import ru.practicum.android.diploma.util.Resource

interface VacanciesInteractor {
    suspend fun searchVacancies(expression: String, page: Int): Resource<VacanciesSearchResult<VacancyPreviewPresent>>
}
