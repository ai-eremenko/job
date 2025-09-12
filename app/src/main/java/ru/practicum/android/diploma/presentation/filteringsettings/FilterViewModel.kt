package ru.practicum.android.diploma.presentation.filteringsettings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filteringsettings.FilterInteractor
import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings

class FilterViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    var currentFilterSettings: FilterSettings? = null
    private val filterStateLiveData = MutableLiveData<FilterSettings?>()
    fun getFilterStateLiveData(): LiveData<FilterSettings?> = filterStateLiveData

    init {
        getFilterSettings()
    }

    private fun getFilterSettings() {
        //currentFilterSettings = filterInteractor.getFilterOptions()
        val settings = FilterSettings(
            null, "Россия, Москва", null, "IT", 40000, true
        )
        currentFilterSettings = settings
        filterStateLiveData.value = currentFilterSettings
    }
}
