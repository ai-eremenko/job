package ru.practicum.android.diploma.domain.models

data class VacancyPreview(
    val id: String,
    val name: String,
    val salaryCurrency: String?,
    val salaryFrom: String?,
    val salaryTo: String?,
    val addressCity: String?,
    val employerName: String,
    val employerLogo: String?
)
