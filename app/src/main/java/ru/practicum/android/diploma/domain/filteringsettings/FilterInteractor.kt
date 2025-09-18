package ru.practicum.android.diploma.domain.filteringsettings

import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings

interface FilterInteractor {
    fun getFilterOptions(): FilterSettings
    fun saveFilterOptions(filter: FilterSettings)
    fun hasActiveFilters(): Boolean
}
