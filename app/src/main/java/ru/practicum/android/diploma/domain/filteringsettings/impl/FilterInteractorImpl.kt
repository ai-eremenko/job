package ru.practicum.android.diploma.domain.filteringsettings.impl

import ru.practicum.android.diploma.domain.filteringsettings.FilterInteractor
import ru.practicum.android.diploma.domain.filteringsettings.FilterRepository
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings

class FilterInteractorImpl(
    val repository: FilterRepository,
) : FilterInteractor {
    override fun getFilterOptions(): FilterSettings {
        return repository.getFilterOptions()
    }

    override fun saveFilterOptions(filter: FilterSettings) {
        repository.saveFilterOptions(filter)
    }

    override fun hasActiveFilters(): Boolean {
        val filter = repository.getFilterOptions()
        return filter.countryName != null ||
                filter.areaId != null ||
                filter.industryId != null ||
                filter.salary != null ||
                filter.onlyWithSalary
    }
}
