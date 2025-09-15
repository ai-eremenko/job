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
                        val countries = loadedAreas.filter { it.parentId == null }
                        screenState.postValue(CountryScreenState.Content(countries))
                    }
                }

                is Resource.Error -> {
                    screenState.postValue(CountryScreenState.Empty)
                }
            }
        }
    }

    fun saveCountry(area: Area) {

    }

}
