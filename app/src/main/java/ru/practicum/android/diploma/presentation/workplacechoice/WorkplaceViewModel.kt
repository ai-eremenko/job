package ru.practicum.android.diploma.presentation.workplacechoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filteringsettings.FilterInteractor
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings

class WorkplaceViewModel(
    val filterInteractor: FilterInteractor
) : ViewModel() {
    private var savedFilter: FilterSettings = FilterSettings()
    private var tempFilter: FilterSettings = FilterSettings()

    private val screenState = MutableLiveData<Pair<String?, String?>>()
    fun getScreenState(): LiveData<Pair<String?, String?>> = screenState

    init {
        savedFilter = filterInteractor.getFilterOptions()
        tempFilter = savedFilter.copy()
        updateScreenState()
    }
    fun saveArea() {
        filterInteractor.saveFilterOptions(tempFilter)
        savedFilter = tempFilter.copy()
    }

    fun clearAreaSelection() {
        tempFilter = tempFilter.copy(
            areaId = null,
            areaName = null
        )
        updateScreenState()
    }

    fun clearCountrySelection() {
        tempFilter = tempFilter.copy(
            areaId = null,
            areaName = null,
            countryId = null,
            countryName = null
        )
        updateScreenState()
    }

    fun setTempCountry(countryId: Int?, countryName: String?) {
        tempFilter = tempFilter.copy(
            countryId = countryId,
            countryName = countryName,
            areaId = null,
            areaName = null
        )
        updateScreenState()
    }

    fun setTempArea(areaId: Int?, areaName: String?) {
        tempFilter = tempFilter.copy(
            areaId = areaId,
            areaName = areaName
        )
        updateScreenState()
    }

    fun getTempCountry(): Pair<Int?, String?> {
        return tempFilter.countryId to tempFilter.countryName
    }

    private fun updateScreenState() {
        screenState.value = tempFilter.countryName to tempFilter.areaName
    }

    fun updateContent() {
        updateScreenState()
    }
}
