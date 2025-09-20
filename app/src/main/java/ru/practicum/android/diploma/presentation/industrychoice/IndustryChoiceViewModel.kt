package ru.practicum.android.diploma.presentation.industrychoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.industrychoice.IndustryInteractor
import ru.practicum.android.diploma.domain.industrychoice.models.Industry
import ru.practicum.android.diploma.util.Resource

class IndustryChoiceViewModel(
    private val interactor: IndustryInteractor
) : ViewModel() {

    private val screenState = MutableLiveData<IndustryChoiceScreenState>()
    fun getScreenState(): LiveData<IndustryChoiceScreenState> = screenState

    init {
        loadIndustries()
    }

    fun saveIndustry(industryId: Int) {
        industries.forEach {
            if (it.id == industryId) {
                interactor.saveIndustry(it)
            }
        }
    }

    private var industries = listOf<Industry>()
    private fun loadIndustries() {
        viewModelScope.launch {
            val result = interactor.getIndustries()
            when (result) {
                is Resource.Success<*> -> {
                    result.data?.let { loadedIndustries ->
                        industries = loadedIndustries
                        screenState.postValue(IndustryChoiceScreenState.Content(loadedIndustries))
                    }
                }

                is Resource.Error<*> -> screenState.postValue(IndustryChoiceScreenState.Empty)
            }
        }
    }
}
