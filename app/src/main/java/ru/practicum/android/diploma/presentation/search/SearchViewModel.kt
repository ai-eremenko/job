package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    private val stateLiveData = MutableLiveData<SearchScreenState>()
    fun observeState(): LiveData<SearchScreenState> = stateLiveData

    private var latestSearchText: String? = null

    private val vacanciesSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
        searchRequest(changedText)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            vacanciesSearchDebounce(changedText)
        }
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {

            renderState(SearchScreenState.Loading)

            viewModelScope.launch {
                searchInteractor
                    .searchVacancies(newSearchText, 1)
                    .collect {
                        when(it) {
                            is Resource.Success<*> -> {
                                if (it.data?.items == null || it.data.found == 0) {
                                    renderState(SearchScreenState.EmptyError)
                                }else{
                                    renderState(SearchScreenState.Content(it.data.items, it.data.found))
                                }
                            }
                            is Resource.Error<*> -> renderState(SearchScreenState.NetworkError)
                        }
                    }
            }
        }
    }

    private fun renderState(state: SearchScreenState) {
        stateLiveData.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 1200L
    }
}
