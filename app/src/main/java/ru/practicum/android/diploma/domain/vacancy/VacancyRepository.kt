package ru.practicum.android.diploma.domain.vacancy

import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface VacancyRepository {
    suspend fun getVacancyById(id: String): Resource<Vacancy>
}
