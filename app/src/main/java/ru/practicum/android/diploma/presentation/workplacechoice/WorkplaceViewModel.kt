package ru.practicum.android.diploma.presentation.workplacechoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.domain.filteringsettings.FilterInteractor
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings
import ru.practicum.android.diploma.util.Resource

class WorkplaceViewModel(
    val filterInteractor: FilterInteractor,
    val areasInteractor: AreasInteractor
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
        viewModelScope.launch {
            val parentArea = areaId?.let { findParentAreaSafely(it) }

            tempFilter = tempFilter.copy(
                areaId = areaId,
                areaName = areaName,
                countryId = tempFilter.countryId ?: parentArea?.id,
                countryName = tempFilter.countryName ?: parentArea?.name
            )

            updateScreenState()
        }
    }

    private suspend fun findParentAreaSafely(areaId: Int): Area? {
        return when (val result = areasInteractor.getParentArea(areaId)) {
            is Resource.Success -> result.data
            is Resource.Error -> null
        }
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
