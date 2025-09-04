package ru.practicum.android.diploma.domain.search.models

sealed interface VacanciesResultModel
data class VacanciesSearchResult<T : VacanciesResultModel>(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<T>
)
