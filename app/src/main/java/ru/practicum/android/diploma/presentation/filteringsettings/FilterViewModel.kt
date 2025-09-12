package ru.practicum.android.diploma.presentation.filteringsettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filteringsettings.FilterInteractor
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings

class FilterViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    var currentFilterSettings = FilterSettings()
    private val filterStateLiveData = MutableLiveData<FilterSettings>()
    fun getFilterStateLiveData(): LiveData<FilterSettings> = filterStateLiveData

    init {
        getFilterSettings()
    }

    private fun getFilterSettings() {
        currentFilterSettings = filterInteractor.getFilterOptions()
        filterStateLiveData.value = currentFilterSettings
    }

    fun clearFilter() {
        currentFilterSettings = FilterSettings()
        filterInteractor.clearFilterOptions()
        filterStateLiveData.value = currentFilterSettings
    }

    fun updateFilterSettings(isChecked: Boolean) {
        currentFilterSettings = currentFilterSettings.copy(onlyWithSalary = isChecked)
        filterInteractor.saveFilterOptions(currentFilterSettings)
        filterStateLiveData.value = currentFilterSettings
    }

    fun hasActiveFilters(): Boolean {
        return currentFilterSettings.hasActiveFilters()
    }
}
