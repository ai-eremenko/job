package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Suppress("DataClassShouldBeImmutable")
@Entity(
    tableName = "favorite_vacancies"
)
data class FavoriteVacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val salary: String,
    val city: String,
    val experience: String,
    val schedule: String,
    val employment: String,
    val contacts: String?,
    val employer: String,
    val skills: String?,
    val url: String?,
    var isFavorite: Boolean = false

) {
    constructor() : this(
        id = "",
        name = "",
        description = "",
        salary = "",
        city = "",
        experience = "",
        schedule = "",
        employment = "",
        contacts = null,
        employer = "",
        skills = null,
        url = "",
        isFavorite = false
    )
}
