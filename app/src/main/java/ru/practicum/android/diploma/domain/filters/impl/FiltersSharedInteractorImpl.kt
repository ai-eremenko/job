package ru.practicum.android.diploma.domain.filters.impl

import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.domain.filters.FiltersSharedInteractor
import ru.practicum.android.diploma.data.storage.Storage

class FiltersSharedInteractorImpl(
    private val storage: Storage
) : FiltersSharedInteractor {

    override fun getCountry(isCurrent: Boolean): Area? {
        return storage.getCountry(isCurrent)
    }

    override fun getRegion(isCurrent: Boolean): Area? {
        return storage.getRegion(isCurrent)
    }
}
