package ru.practicum.android.diploma.presentation.regionchoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.domain.filters.FiltersInteractor
import ru.practicum.android.diploma.domain.filters.FiltersSharedInteractor
import ru.practicum.android.diploma.domain.filters.FiltersSharedInteractorSave

sealed class AreasState {
    object Loading : AreasState()
    object Empty : AreasState()
    object Error : AreasState()
    data class Content(val areas: List<Area>) : AreasState()
}

class RegionViewModel(
    private val interactor: FiltersInteractor,
    private val sharedInteractor: FiltersSharedInteractor,
    private val sharedInteractorSave: FiltersSharedInteractorSave
) : ViewModel() {

    private val _screenState = MutableLiveData<AreasState>()
    val screenState: LiveData<AreasState> = _screenState

    init {
        loadRegions()
    }

    fun loadRegions() {
        _screenState.value = AreasState.Loading

        val country = sharedInteractor.getCountry(isCurrent = true)

        viewModelScope.launch {
            if (country == null) {
                interactor.getAllRegions().collect { regions ->
                    if (regions.isNotEmpty()) {
                        _screenState.value = AreasState.Content(
                            regions.filter { it.parentId != null && it.parentId != 1001 }
                        )
                    } else {
                        downloadAreasToBase()
                    }
                }
            } else {
                interactor.getRegionsByParent(country.id).collect { regions ->
                    if (regions.isNotEmpty()) {
                        _screenState.value = AreasState.Content(regions)
                    } else {
                        downloadAreasToBase()
                    }
                }
            }
        }
    }

    private suspend fun downloadAreasToBase() {
        interactor.downloadAreas().collect { result ->
            val dtoList = result.first
            val status = result.second

            if (dtoList == null) {
                _screenState.value = if (status == STATUS_OK) AreasState.Empty else AreasState.Error
            } else {
                // Преобразуем DTO в доменные модели
                val areas = dtoList.areas.map { dto ->
                    Area(
                        id = dto.id,
                        name = dto.name,
                        parentId = dto.parentId, // здесь уже Int?, преобразование не нужно
                        areas = emptyList() // или dto.areas.map { ... }, если нужно
                    )
                }

                interactor.insertAreas(areas)

                val cisRegions = areas.filter { it.parentId != null && it.parentId != 1001 }
                _screenState.value = AreasState.Content(cisRegions)
            }
        }
    }

    fun search(query: String) {
        val country = sharedInteractor.getCountry(isCurrent = true)

        viewModelScope.launch {
            if (country == null) {
                interactor.getRegionsByName(query).collect { areas ->
                    val filtered = areas.filter { it.parentId != null && it.parentId != 1001 }
                    _screenState.value = if (filtered.isEmpty()) AreasState.Empty else AreasState.Content(filtered)
                }
            } else {
                interactor.getRegionsByNameAndParent(query, country.id).collect { areas ->
                    _screenState.value = if (areas.isEmpty()) AreasState.Empty else AreasState.Content(areas)
                }
            }
        }
    }

    fun saveAndExit(selectedRegion: Area, navController: NavController) {
        viewModelScope.launch {
            sharedInteractorSave.saveRegion(selectedRegion, isCurrent = true)

            val country = sharedInteractor.getCountry(isCurrent = true)
            if (country == null && selectedRegion.parentId != null) {
                val parentId = selectedRegion.parentId
                interactor.getCountryById(parentId).collect { parentCountry ->
                    sharedInteractorSave.saveCountry(parentCountry, isCurrent = true)
                    navController.navigateUp()
                }
            } else {
                navController.navigateUp()
            }
        }
    }

    companion object {
        const val STATUS_OK = 200
    }
}
