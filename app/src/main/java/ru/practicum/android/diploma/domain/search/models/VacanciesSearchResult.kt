package ru.practicum.android.diploma.domain.search.models

data class VacanciesSearchResult(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyPreview>
)
