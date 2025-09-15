package ru.practicum.android.diploma.presentation.workplacechoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filteringsettings.FilterInteractor
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings

class WorkplaceViewModel(
    val filterInteractor: FilterInteractor
) : ViewModel() {
    private var filter: FilterSettings = FilterSettings()

    private val screenState = MutableLiveData<Pair<String?, String?>>()
    fun getScreenState(): LiveData<Pair<String?, String?>> = screenState

    init {
        filter = filterInteractor.getFilterOptions()
        screenState.value = filter.countryName to filter.areaName
    }
    fun saveArea() {
        filterInteractor.saveFilterOptions(filter)
    }

    fun clearAreaSelection() {
        filter = filter.copy(
            areaId = null,
            areaName = null
        )
        screenState.value = filter.countryName to filter.areaName
    }

    fun clearCountrySelection() {
        filter = filter.copy(
            areaId = null,
            areaName = null,
            countryId = null,
            countryName = null
        )
        screenState.value = filter.countryName to filter.areaName
    }

    fun updateContent() {
        //подтянуть временный выбор
    }
}
