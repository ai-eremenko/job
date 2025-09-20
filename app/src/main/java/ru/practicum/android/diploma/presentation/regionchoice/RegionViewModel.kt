package ru.practicum.android.diploma.presentation.regionchoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.presentation.regionchoice.models.RegionState
import ru.practicum.android.diploma.util.Resource

class RegionViewModel(
    private val countryId: Int,
    private val interactor: AreasInteractor
) : ViewModel() {

    private val _screenState = MutableLiveData<RegionState>()
    val screenState: LiveData<RegionState> = _screenState

    private var allAreas: List<Area> = emptyList()

    init {
        loadRegions()
    }

    private fun loadRegions() {
        viewModelScope.launch {
            when (val result = interactor.getAreas()) {
                is Resource.Success -> {
                    allAreas = result.data ?: emptyList()
                    val regions = filterRegions()
                    if (regions.isEmpty()) {
                        _screenState.value = RegionState.Empty
                    } else {
                        _screenState.value = RegionState.Content(regions)
                    }
                }

                is Resource.Error -> {
                    _screenState.value = RegionState.Empty
                }
            }
        }
    }

    private fun filterRegions(): List<Area> {
        val allRegions = flattenAreasRecursive(allAreas)

        return if (countryId == 0) {
            allRegions.filter { it.parentId != null }
                .sortedBy { it.name }
        } else {
            allRegions.filter { it.parentId == countryId }
                .sortedBy { it.name }
        }
    }

    private fun flattenAreasRecursive(areas: List<Area>): List<Area> {
        return areas.flatMap { area ->
            listOf(area) + flattenAreasRecursive(area.areas)
        }
    }

    fun selectArea(area: Area, onSelected: (Area) -> Unit) {
        onSelected(area)
    }
}
