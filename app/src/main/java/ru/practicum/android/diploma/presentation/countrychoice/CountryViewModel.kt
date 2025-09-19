package ru.practicum.android.diploma.presentation.countrychoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.countrychoice.CountryUseCase
import ru.practicum.android.diploma.presentation.countrychoice.models.CountryScreenState
import ru.practicum.android.diploma.util.Resource

class CountryViewModel(
    val useCase: CountryUseCase
) : ViewModel() {
    private val screenState = MutableLiveData<CountryScreenState>()
    fun getScreenState(): LiveData<CountryScreenState> = screenState

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            when (val result = useCase.getCountries()) {
                is Resource.Success -> {
                    result.data?.let { countries ->
                        val sortedCountries = countries.sortedBy { it.name }
                        screenState.postValue(CountryScreenState.Content(sortedCountries))
                    } ?: run {
                        screenState.postValue(CountryScreenState.Empty)
                    }
                }

                is Resource.Error -> {
                    screenState.postValue(CountryScreenState.Empty)
                }
            }
        }
    }
}
