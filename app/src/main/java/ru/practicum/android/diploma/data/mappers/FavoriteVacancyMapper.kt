package ru.practicum.android.diploma.data.mappers

import com.google.gson.Gson
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.domain.search.models.VacancyPreviewPresent
import ru.practicum.android.diploma.domain.vacancy.models.Contacts
import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent

class FavoriteVacancyMapper {
    private val gson = Gson()

    fun toEntity(vacancy: VacancyPresent): FavoriteVacancyEntity {
        return FavoriteVacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            description = vacancy.description,
            salary = vacancy.salary,
            city = vacancy.address,
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            contacts = vacancy.contacts?.let { gson.toJson(it) },
            employer = vacancy.employerName,
            skills = vacancy.skills,
            url = vacancy.url,
            isFavorite = vacancy.isFavorite,
            urlLink = vacancy.urlLink
        )
    }

    fun toDomain(vacancy: FavoriteVacancyEntity): VacancyPresent {
        return VacancyPresent(
            id = vacancy.id,
            name = vacancy.name,
            description = vacancy.description,
            salary = vacancy.salary,
            address = vacancy.city,
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            contacts = vacancy.contacts?.let { gson.fromJson(it, Contacts::class.java) },
            employerName = vacancy.employer,
            skills = vacancy.skills,
            url = vacancy.url,
            isFavorite = vacancy.isFavorite,
            urlLink = vacancy.urlLink
        )
    }

    fun mapToPreview(vacancy: VacancyPresent): VacancyPreviewPresent {
        return VacancyPreviewPresent(
            id = vacancy.id,
            name = vacancy.name,
            url = vacancy.url ?: "",
            salary = vacancy.salary,
            addressCity = vacancy.address,
            employerName = vacancy.employerName
        )
    }

    fun mapToPreviewList(vacancies: List<VacancyPresent>): List<VacancyPreviewPresent> {
        return vacancies.map { mapToPreview(it) }
    }
}
