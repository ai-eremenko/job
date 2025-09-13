package ru.practicum.android.diploma.domain.areas.impl

import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.AreasRepository
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatus

class AreasInteractorImpl(
    private val repository: AreasRepository
) : AreasInteractor {
    override suspend fun getCountries(): Resource<List<Area>> {
        return when(val result = repository.getAreas()) {
            is Resource.Success -> {
                Resource.Success(result.data?.filter { it.parentId == null }.orEmpty())
            }
            is Resource.Error -> {
                Resource.Error(ResponseStatus.NOT_FOUND)
            }
        }
    }

    override suspend fun getAreasForCountry(areaParentId: Int): Resource<List<Area>> {
        return when(val result = repository.getAreas()) {
            is Resource.Success -> {
                Resource.Success(result.data?.firstOrNull { it.parentId == areaParentId }?.areas.orEmpty())
            }
            is Resource.Error -> {
                Resource.Error(ResponseStatus.NOT_FOUND)
            }
        }
    }
}
