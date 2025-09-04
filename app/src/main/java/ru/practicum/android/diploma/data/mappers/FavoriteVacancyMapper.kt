package ru.practicum.android.diploma.data.mappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.domain.vacancy.models.Contacts
import ru.practicum.android.diploma.domain.vacancy.models.Employer
import ru.practicum.android.diploma.domain.vacancy.models.Salary
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy

class FavoriteVacancyMapper {
    private val gson = Gson()

    fun toEntity(vacancy: Vacancy): FavoriteVacancyEntity {
        return FavoriteVacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            description = vacancy.description,
            salary = vacancy.salary?.let { gson.toJson(it) },
            city = vacancy.city,
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            contacts = vacancy.contacts?.let { gson.toJson(it) },
            employer = gson.toJson(vacancy.employer),
            skills = vacancy.skills?.let { gson.toJson(it) },
            url = vacancy.url
        )
    }

    fun toDomain(vacancy: FavoriteVacancyEntity): Vacancy {
        return Vacancy(
            id = vacancy.id,
            name = vacancy.name,
            description = vacancy.description,
            salary = vacancy.salary?.let { gson.fromJson(it, Salary::class.java) },
            city = vacancy.city,
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            contacts = vacancy.contacts?.let { gson.fromJson(it, Contacts::class.java) },
            employer = gson.fromJson(vacancy.employer, Employer::class.java),
            skills = vacancy.skills?.let {
                gson.fromJson(it, object : TypeToken<List<String>>() {}.type) },
            url = vacancy.url
        )
    }
}
