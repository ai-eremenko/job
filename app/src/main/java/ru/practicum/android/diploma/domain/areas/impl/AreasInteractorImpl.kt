package ru.practicum.android.diploma.domain.areas.impl

import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.AreasRepository
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatus

class AreasInteractorImpl(
    private val repository: AreasRepository
) : AreasInteractor {

    override suspend fun getAreas(): Resource<List<Area>> {
        return repository.getAreas()
    }

    override suspend fun getCountries(): Resource<List<Area>> {
        return when (val areas = repository.getAreas()) {
            is Resource.Error -> areas
            is Resource.Success -> {
                val data = areas.data ?: return Resource.Error(ResponseStatus.NOT_FOUND)
                val countries = findRootAreas(data)
                Resource.Success(countries)
            }
        }
    }

    private fun findRootAreas(areas: List<Area>): List<Area> {
        return areas.filter { it.parentId == null }
    }
}
