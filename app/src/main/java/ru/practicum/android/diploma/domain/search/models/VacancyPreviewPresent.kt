package ru.practicum.android.diploma.domain.search.models

data class VacancyPreviewPresent(
    val id: String,
    val name: String,
    val url: String,
    val salary: String?,
    val addressCity: String?,
    val employerName: String,
) : VacanciesResultModel
