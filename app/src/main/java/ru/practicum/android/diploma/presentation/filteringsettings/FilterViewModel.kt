package ru.practicum.android.diploma.presentation.filteringsettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filteringsettings.FilterInteractor
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings
import ru.practicum.android.diploma.presentation.filteringsettings.models.FilterScreenState

class FilterViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {
    private val filterStateLiveData = MutableLiveData<FilterScreenState>()
    private val hasChangesLiveData = MutableLiveData<Boolean>(false)
    fun getFilterStateLiveData(): LiveData<FilterScreenState> = filterStateLiveData
    fun getHasChangesLiveData(): LiveData<Boolean> = hasChangesLiveData

    init {
        loadFilterSettings()
    }

    private fun loadFilterSettings() {
        val filter = filterInteractor.getFilterOptions()
        updateScreenState(filter)
    }

    fun clearFilter() {
        val emptyFilter = FilterSettings()
        filterInteractor.saveFilterOptions(emptyFilter)
        updateScreenState(emptyFilter)
        hasChangesLiveData.value = true
    }

    fun updateOnlyWithSalary(isChecked: Boolean) {
        val currentFilter = filterInteractor.getFilterOptions()
        val updatedFilter = currentFilter.copy(onlyWithSalary = isChecked)
        saveAndUpdate(updatedFilter)
    }

    fun updateSalary(salary: String) {
        val currentFilter = filterInteractor.getFilterOptions()
        val salaryValue = salary.takeIf { it.isNotBlank() }?.toIntOrNull()
        val updatedFilter = currentFilter.copy(salary = salaryValue)
        saveAndUpdate(updatedFilter)
    }

    private fun saveAndUpdate(filter: FilterSettings) {
        filterInteractor.saveFilterOptions(filter)
        updateScreenState(filter)
        hasChangesLiveData.value = true
    }

    private fun updateScreenState(filter: FilterSettings) {
        val state = if (filterInteractor.hasActiveFilters()) {
            FilterScreenState.Content(filter)
        } else {
            FilterScreenState.Empty
        }
        filterStateLiveData.value = state
    }

    fun updateContent() {
        loadFilterSettings()
    }

    fun clearWorkplaceSelection() {
        val currentFilter = filterInteractor.getFilterOptions()
        val updatedFilter = currentFilter.copy(
            countryId = null,
            countryName = null,
            areaId = null,
            areaName = null
        )
        saveAndUpdate(updatedFilter)
    }

    fun clearIndustrySelection() {
        val currentFilter = filterInteractor.getFilterOptions()
        val updatedFilter = currentFilter.copy(
            industryId = null,
            industryName = null
        )
        saveAndUpdate(updatedFilter)
    }

    fun resetChangesFlag() {
        hasChangesLiveData.value = false
    }
}
