package ru.practicum.android.diploma.domain.filters.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.domain.areas.AreasRepository
import ru.practicum.android.diploma.data.dto.responses.areas.AreasListDTO
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.domain.filters.FiltersInteractor

class FiltersInteractorImpl(
    private val areasRepository: AreasRepository
) : FiltersInteractor {

    override suspend fun getAllRegions(): Flow<List<Area>> = flow {
        val result = areasRepository.getAreas()
        emit(result.data ?: emptyList())
    }

    override suspend fun getRegionsByParent(parentId: Int?): Flow<List<Area>> = flow {
        val result = areasRepository.getAreas()
        val filtered = result.data?.filter { it.parentId == parentId } ?: emptyList()
        emit(filtered)
    }

    override suspend fun getRegionsByName(query: String): Flow<List<Area>> = flow {
        val result = areasRepository.getAreas()
        val filtered = result.data?.filter { it.name.contains(query, ignoreCase = true) } ?: emptyList()
        emit(filtered)
    }

    override suspend fun getRegionsByNameAndParent(query: String, parentId: Int?): Flow<List<Area>> = flow {
        val result = areasRepository.getAreas()
        val filtered = result.data?.filter { it.parentId == parentId && it.name.contains(query, ignoreCase = true) } ?: emptyList()
        emit(filtered)
    }

    override suspend fun downloadAreas(): Flow<Pair<AreasListDTO?, Int>> = flow {
        val result: Pair<AreasListDTO?, Int> = areasRepository.downloadAreas()
        emit(result)
    }

    override suspend fun insertAreas(areas: List<Area>) {
        areasRepository.insertAreas(areas)
    }

    override suspend fun getCountryById(id: Int): Flow<Area> = flow {
        val result = areasRepository.getAreas()
        val country = result.data?.find { it.id == id }
        if (country != null) emit(country)
    }
}
