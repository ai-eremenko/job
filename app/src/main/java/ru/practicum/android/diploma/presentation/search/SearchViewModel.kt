package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filteringsettings.FilterInteractor
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.VacancyPreviewPresent
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResponseStatus
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<SearchScreenState>()
    fun observeState(): LiveData<SearchScreenState> = stateLiveData

    private val filterState = MutableLiveData<Boolean>()
    fun getFilterState(): LiveData<Boolean> = filterState

    private var latestSearchText: String? = null

    private var totalPages = 0
    private var currentPage = 1
    private var searchText = ""

    private val vacanciesSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
        searchRequest(changedText)
    }

    init {
        checkFilterState()
    }

    private fun checkFilterState() {
        filterState.value = filterInteractor.hasActiveFilters()
    }

    fun searchDebounce(changedText: String, force: Boolean = false) {
        if (force) {
            latestSearchText = null
        }

        if (latestSearchText != changedText) {
            latestSearchText = changedText
            vacanciesSearchDebounce(changedText)
        }
    }

    val vacancyChannel = Channel<List<VacancyPreviewPresent>>()
    val toastChannel = Channel<ResponseStatus>()

    fun newPageRequest() {
        viewModelScope.launch {
            searchInteractor
                .searchVacancies(searchText, ++currentPage)
                .collect {
                    when (it) {
                        is Resource.Success<*> -> {
                            if (it.data?.items == null || it.data.found == 0) {
                                renderState(SearchScreenState.NetworkError)
                            } else {
                                vacancyChannel.send(it.data.items)
                            }
                        }

                        is Resource.Error<*> -> it.message?.let { element -> toastChannel.send(element) }
                    }
                }
        }
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(SearchScreenState.Loading)

            searchText = newSearchText

            viewModelScope.launch {
                searchInteractor
                    .searchVacancies(newSearchText, 1)
                    .collect {
                        when (it) {
                            is Resource.Success<*> -> {
                                if (it.data?.items == null || it.data.found == 0) {
                                    renderState(SearchScreenState.EmptyError)
                                } else {
                                    renderState(SearchScreenState.Content(it.data.items, it.data.found))
                                    totalPages = it.data.pages
                                }
                            }

                            is Resource.Error<*> -> renderState(SearchScreenState.NetworkError)
                        }
                    }
            }
        }
    }

    fun isMorePage(): Boolean {
        return totalPages >= currentPage
    }

    private fun renderState(state: SearchScreenState) {
        stateLiveData.postValue(state)
    }

    fun updateFilterState() {
        checkFilterState()
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 1200L
    }
}
