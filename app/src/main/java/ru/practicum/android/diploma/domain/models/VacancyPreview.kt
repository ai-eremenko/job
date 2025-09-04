package ru.practicum.android.diploma.domain.models

data class VacancyPreview(
    val id: String,
    val name: String,
    val albumCoverUrl: String?,
    val salary: String?,
    val addressCity: String?,
    val employerName: String
)
