package ru.practicum.android.diploma.domain.industrychoice

import ru.practicum.android.diploma.domain.industrychoice.models.Industry
import ru.practicum.android.diploma.util.Resource

interface IndustryInteractor {
    suspend fun getIndustries(): Resource<List<Industry>>
    fun saveIndustry(industry: Industry)
}
