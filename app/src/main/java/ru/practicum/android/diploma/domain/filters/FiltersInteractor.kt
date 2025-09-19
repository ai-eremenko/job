package ru.practicum.android.diploma.domain.filters

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.responses.areas.AreasListDTO
import ru.practicum.android.diploma.domain.areas.models.Area

interface FiltersInteractor {
    suspend fun insertAreas(areas: List<Area>)
    suspend fun getAllRegions(): Flow<List<Area>>
    suspend fun getRegionsByParent(parentId: Int?): Flow<List<Area>>
    suspend fun getRegionsByName(name: String): Flow<List<Area>>
    suspend fun getRegionsByNameAndParent(name: String, parentId: Int?): Flow<List<Area>>
    suspend fun getCountryById(id: Int): Flow<Area>
    suspend fun downloadAreas(): Flow<Pair<AreasListDTO?, Int>>
}
