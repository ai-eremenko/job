package ru.practicum.android.diploma.domain.areas.impl

import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.AreasRepository
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.util.Resource

class AreasInteractorImpl(
    private val repository: AreasRepository
) : AreasInteractor {

    override suspend fun getAreas(): Resource<List<Area>> {
        return repository.getAreas()
    }

    override suspend fun getAllRegions(): Resource<List<Area>> {
        return when (val areasResult = repository.getAreas()) {
            is Resource.Success -> {
                val allRegions = mutableListOf<Area>()
                collectAllRegions(areasResult.data!!, allRegions)
                Resource.Success(allRegions)
            }
            is Resource.Error -> areasResult
        }
    }

    override suspend fun getRegionsByCountryId(countryId: Int): Resource<List<Area>> {
        return when (val areasResult = repository.getAreas()) {
            is Resource.Success -> {
                val regions = areasResult.data!!.filter { it.parentId == countryId }
                Resource.Success(regions)
            }
            is Resource.Error -> areasResult
        }
    }

    private fun collectAllRegions(areas: List<Area>, result: MutableList<Area>) {
        areas.forEach { area ->
            if (area.parentId != null) {
                result.add(area)
            }
            if (area.areas.isNotEmpty()) {
                collectAllRegions(area.areas, result)
            }
        }
    }
}
