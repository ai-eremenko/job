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

    override suspend fun getParentArea(areaId: Int): Resource<Area?> {
        val areasResult = repository.getAreas()
        return when(areasResult) {
            is Resource.Error<*> -> Resource.Error(ResponseStatus.NOT_FOUND)
            is Resource.Success<*> -> findArea(areaId, areasResult.data)
        }
    }

    private fun findArea(areaId: Int, areas: List<Area>?): Resource<Area?> {
        return if(areas != null) {
            Resource.Success(findAreaById(areas, areaId))
        } else Resource.Error(ResponseStatus.NOT_FOUND)
    }

    private fun findAreaById(areas: List<Area>, targetId: Int): Area? {
        for (area in areas) {
            if (area.id == targetId) {
                return area
            }
            val foundInChildren = findAreaById(area.areas, targetId)
            if (foundInChildren != null) {
                return foundInChildren
            }
        }
        return null
    }
}
