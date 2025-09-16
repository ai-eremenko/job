package ru.practicum.android.diploma.presentation.regionchoice

import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.util.Resource

sealed class RegionState {
    object Loading : RegionState()
    object Empty : RegionState()
    object Error : RegionState()
    data class Content(val areasList: List<Area>) : RegionState()
}

class RegionViewModel(
    private val interactor: AreasInteractor
) : ViewModel() {

    private val _screenState = MutableLiveData<RegionState>()
    val screenState: LiveData<RegionState> = _screenState

    private val allRegions = mutableListOf<Area>()
    private var searchJob: Job? = null

    init {
        loadRegions()
    }

    private fun loadRegions() {
        viewModelScope.launch {
            _screenState.value = RegionState.Loading
            try {
                when (val result = interactor.getAreas()) {
                    is Resource.Success -> {
                        val regions = result.data ?: emptyList()
                        allRegions.clear()
                        allRegions.addAll(regions)
                        _screenState.value =
                            if (regions.isEmpty()) RegionState.Empty
                            else RegionState.Content(regions)
                    }
                    is Resource.Error -> _screenState.value = RegionState.Error
                }
            } catch (e: Exception) {
                _screenState.value = RegionState.Error
            }
        }
    }

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(200)
            val filtered = if (query.isBlank()) allRegions
            else allRegions.filter { it.name.contains(query, ignoreCase = true) }
            _screenState.value =
                if (filtered.isEmpty()) RegionState.Empty
                else RegionState.Content(filtered)
        }
    }

    fun saveAndExit(selectedArea: Area, onExit: () -> Unit) {
        onExit()
    }
}
