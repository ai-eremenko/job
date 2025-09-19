package ru.practicum.android.diploma.domain.filters

import ru.practicum.android.diploma.domain.areas.models.Area

interface FiltersSharedInteractorSave {
    fun saveCountry(country: Area?, isCurrent: Boolean)
    fun saveRegion(region: Area?, isCurrent: Boolean)
}
