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
    val skills: String?,
    val url: String?,
    var isFavorite: Boolean,
    val urlLink: String
) {
    fun copyWithFavorite(isFavorite: Boolean): VacancyPresent {
        return this.copy(isFavorite = isFavorite)
    }
}
