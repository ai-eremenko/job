package ru.practicum.android.diploma.presentation.regionchoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.util.Resource
import java.io.IOException
import java.net.SocketTimeoutException

sealed class RegionState {
    object Loading : RegionState()
    object Empty : RegionState()
    object Error : RegionState()
    data class Content(val areasList: List<Area>) : RegionState()
}

class RegionViewModel(private val interactor: AreasInteractor) : ViewModel() {

    private val _screenState = MutableLiveData<RegionState>()
    val screenState: LiveData<RegionState> = _screenState

    private var allAreas: List<Area> = emptyList()
    private var searchJob: Job? = null
    private var isLoaded = false

    init {
        loadRegions()
    }

    private fun loadRegions() {
        viewModelScope.launch {
            _screenState.value = RegionState.Loading
            try {
                when (val result = interactor.getAreas()) {
                    is Resource.Success -> {
                        allAreas = result.data ?: emptyList()
                        isLoaded = true
                        _screenState.value = RegionState.Empty
                    }
                    is Resource.Error -> _screenState.value = RegionState.Error
                }
            } catch (e: SocketTimeoutException) {
                _screenState.value = RegionState.Error
            } catch (e: IOException) {
                _screenState.value = RegionState.Error
            }
        }
    }

    fun search(query: String, countryId: Int?) {
        if (!isLoaded) return
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(150L)
            val filteredRegions = allAreas
                .firstOrNull { it.id == countryId }
                ?.areas
                ?.filter { it.name.contains(query, ignoreCase = true) }
                ?.sortedBy { it.name }
                ?: emptyList()

            _screenState.value = if (filteredRegions.isEmpty()) RegionState.Empty
            else RegionState.Content(filteredRegions)
        }
    }

    fun selectArea(area: Area, onSelected: (Area) -> Unit) {
        onSelected(area)
    }
}
