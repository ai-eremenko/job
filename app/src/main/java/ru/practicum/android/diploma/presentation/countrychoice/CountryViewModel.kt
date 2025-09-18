package ru.practicum.android.diploma.presentation.countrychoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.areas.AreasInteractor
import ru.practicum.android.diploma.domain.areas.models.Area
import ru.practicum.android.diploma.presentation.countrychoice.models.CountryScreenState
import ru.practicum.android.diploma.util.Resource

class CountryViewModel(
    val interactor: AreasInteractor
) : ViewModel() {
    private val screenState = MutableLiveData<CountryScreenState>()
    fun getScreenState(): LiveData<CountryScreenState> = screenState

    init {
        loadAreas()
    }

    private fun loadAreas() {
        viewModelScope.launch {
            when (val result = interactor.getAreas()) {
                is Resource.Success -> {
                    result.data?.let { loadedAreas ->
                        val countries = findRootAreas(loadedAreas)
                        val sortedCountries = countries.sortedBy { it.name }
                        screenState.postValue(CountryScreenState.Content(sortedCountries))
                    }
                }
                is Resource.Error -> {
                    screenState.postValue(CountryScreenState.Empty)
                }
            }
        }
    }

    private fun findRootAreas(areas: List<Area>): List<Area> {
        val countries = mutableListOf<Area>()

        fun findCountriesRecursive(currentAreas: List<Area>) {
            for (area in currentAreas) {
                if (area.parentId == null) {
                    countries.add(area)
                }

                area.areas?.let { childAreas ->
                    findCountriesRecursive(childAreas)
                }
            }
        }

        findCountriesRecursive(areas)
        return countries
    }
}
