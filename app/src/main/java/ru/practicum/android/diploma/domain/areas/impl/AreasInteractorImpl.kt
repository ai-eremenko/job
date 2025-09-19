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
}
