package ru.practicum.android.diploma.domain.areas.impl

import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.AreasRepository
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.util.Resource

class AreasInteractorImpl(
    private val repository: AreasRepository
) : AreasInteractor {
    private var cachedResult: Resource<List<Area>>? = null

    override suspend fun getAreas(): Resource<List<Area>> {
        return cachedResult ?: repository.getAreas().also { cachedResult = it }
    }
}
