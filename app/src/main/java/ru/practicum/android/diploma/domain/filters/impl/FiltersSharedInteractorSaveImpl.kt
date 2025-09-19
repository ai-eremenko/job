package ru.practicum.android.diploma.domain.filters.impl

import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.domain.filters.FiltersSharedInteractorSave
import ru.practicum.android.diploma.data.storage.Storage

class FiltersSharedInteractorSaveImpl(
    private val storage: Storage
) : FiltersSharedInteractorSave {

    override fun saveCountry(country: Area?, isCurrent: Boolean) {
        storage.saveCountry(country, isCurrent)
    }

    override fun saveRegion(region: Area?, isCurrent: Boolean) {
        storage.saveRegion(region, isCurrent)
    }
}
