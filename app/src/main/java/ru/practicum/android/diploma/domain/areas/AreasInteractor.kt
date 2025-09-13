package ru.practicum.android.diploma.domain.areas

import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.util.Resource

interface AreasInteractor {
    suspend fun getCountries(): Resource<List<Area>>
    suspend fun getAreasForCountry(areaParentId: Int): Resource<List<Area>>
}
