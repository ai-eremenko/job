package ru.practicum.android.diploma.domain.filters


import ru.practicum.android.diploma.domain.areas.models.Area

interface FiltersSharedInteractor {
    fun getCountry(isCurrent: Boolean): Area?
    fun getRegion(isCurrent: Boolean): Area?
}
