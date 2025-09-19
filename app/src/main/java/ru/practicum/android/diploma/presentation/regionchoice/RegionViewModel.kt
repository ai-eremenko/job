package ru.practicum.android.diploma.presentation.regionchoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.domain.areas.models.AreasState
import ru.practicum.android.diploma.domain.filters.FiltersSharedInteractor
import ru.practicum.android.diploma.domain.filters.FiltersSharedInteractorSave
import ru.practicum.android.diploma.util.Resource

class RegionViewModel(
    private val areasInteractor: AreasInteractor,
    private val sharedInteractor: FiltersSharedInteractor,
    private val sharedInteractorSave: FiltersSharedInteractorSave
) : ViewModel() {

    private val _screenState = MutableLiveData<AreasState>()
    val screenState: LiveData<AreasState> = _screenState

    private var allAreas: List<Area> = emptyList()
    private var currentCountryId: Int? = null
    private var currentRegions: List<Area> = emptyList()

    init {
        viewModelScope.launch { loadAreas() }
    }


    suspend fun loadAreas() {
        _screenState.value = AreasState.Loading
        when (val result = areasInteractor.getAreas()) {
            is Resource.Success<*> -> {
                allAreas = result.data as? List<Area> ?: emptyList()
                val savedCountry = sharedInteractor.getCountry(true)
                savedCountry?.let { setCountry(it.id) }
                _screenState.value = AreasState.Content(currentRegions.sortedBy { it.name })
            }
            is Resource.Error<*> -> {
                _screenState.value = AreasState.Error
            }
        }
    }

    fun getCountries(): List<Area> = allAreas.filter { it.parentId == null }.sortedBy { it.name }

    fun setCountry(countryId: Int) {
        currentCountryId = countryId
        currentRegions = allAreas.firstOrNull { it.id == countryId }?.areas ?: emptyList()
        _screenState.value = AreasState.Content(currentRegions.sortedBy { it.name })
    }

    fun search(query: String) {
        if (query.isBlank()) {
            _screenState.value = AreasState.Content(currentRegions.sortedBy { it.name })
            return
        }
        val filtered = currentRegions.filter { it.name.contains(query, ignoreCase = true) }
        _screenState.value = if (filtered.isEmpty()) AreasState.Empty else AreasState.Content(filtered.sortedBy { it.name })
    }

    fun saveAndExit(selectedRegion: Area, onFinish: () -> Unit) {
        viewModelScope.launch {
            // Сохраняем регион
            sharedInteractorSave.saveRegion(selectedRegion, isCurrent = true)

            if (currentCountryId == null && selectedRegion.parentId != null) {
                val parentCountry = allAreas.firstOrNull { it.id == selectedRegion.parentId }
                parentCountry?.let {
                    sharedInteractorSave.saveCountry(it, isCurrent = true)
                    currentCountryId = it.id
                }
            }

            onFinish()
        }
    }
}
