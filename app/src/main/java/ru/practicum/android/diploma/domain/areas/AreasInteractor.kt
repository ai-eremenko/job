package ru.practicum.android.diploma.domain.areas

import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.util.Resource

interface AreasInteractor {
    suspend fun getAreas(): Resource<List<Area>>
    suspend fun getAllRegions(): Resource<List<Area>>
    suspend fun getRegionsByCountryId(countryId: Int): Resource<List<Area>>
}
