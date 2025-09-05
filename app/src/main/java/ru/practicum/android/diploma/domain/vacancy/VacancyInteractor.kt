package ru.practicum.android.diploma.domain.vacancy

import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent
import ru.practicum.android.diploma.util.Resource

interface VacancyInteractor {
    suspend fun getVacancyById(id: String): Resource<VacancyPresent>
}
