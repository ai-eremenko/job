package ru.practicum.android.diploma.presentation.regionchoice

import android.util.Log
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
                            if (regions.isEmpty()) {
                                RegionState.Empty
                            } else {
                                RegionState.Content(regions)
                            }
                    }
                    is Resource.Error -> {
                        _screenState.value = RegionState.Error
                    }
                }
            } catch (e: SocketTimeoutException) {
                Log.e("RegionViewModel", "Таймаут при загрузке регионов", e)
                _screenState.postValue(RegionState.Error)
            } catch (e: IOException) {
                Log.e("RegionViewModel", "Ошибка загрузки регионов", e)
                _screenState.postValue(RegionState.Error)
            }
        }
    }

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DELAY)
            val filtered = if (query.isBlank()) {
                allRegions
            } else {
                allRegions.filter { it.name.contains(query, ignoreCase = true) }
            }
            _screenState.value =
                if (filtered.isEmpty()) {
                    RegionState.Empty
                } else {
                    RegionState.Content(filtered)
                }
        }
    }

    fun saveAndExit(selectedArea: Area, onExit: () -> Unit) {
        onExit()
    }

    companion object {
        private const val SEARCH_DELAY = 200L
    }
}
