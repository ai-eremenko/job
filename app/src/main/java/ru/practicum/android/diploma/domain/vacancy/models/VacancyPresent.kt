package ru.practicum.android.diploma.domain.vacancy.models

data class VacancyPresent(
    val id: String,
    val name: String,
    val description: String,
    val salary: String,
    val address: String,
    val experience: String,
    val schedule: String,
    val employment: String,
    val contacts: Contacts?,
    val employerName: String,
    val skills: List<String>?,
    val url: String?,
    val isFavorite: Boolean,
    val urlLink: String
)
