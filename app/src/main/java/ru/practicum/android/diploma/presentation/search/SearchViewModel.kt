package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.VacancyPreviewPresent
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    private val stateLiveData = MutableLiveData<SearchScreenState>()
    fun observeState(): LiveData<SearchScreenState> = stateLiveData

    private var latestSearchText: String? = null

    private var totalPages = 0
    private var currentPage = 1
    private var searchText = ""
    private var currentVacancies = emptyList<VacancyPreviewPresent>()
    private var totalFound = 0

    private var isNextPageLoading = false
    private var currentSearchJob: Job? = null
    private var currentPaginationJob: Job? = null

    private val vacanciesSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
        searchRequest(changedText)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            vacanciesSearchDebounce(changedText)
        }
    }

    val vacancyChannel = Channel<List<VacancyPreviewPresent>>()

    fun newPageRequest() {
        if (currentPage >= totalPages) return
        if (isNextPageLoading) return

        currentPaginationJob?.cancel()

        isNextPageLoading = true
        renderState(SearchScreenState.Content(currentVacancies, totalFound, isLoadingMore = true))

        currentPaginationJob = viewModelScope.launch {
            searchInteractor
                .searchVacancies(searchText, currentPage + 1)
                .collect { resource ->
                    isNextPageLoading = false
                    when (resource) {
                        is Resource.Success -> {
                            val newItems = resource.data?.items ?: emptyList()
                            if (newItems.isNotEmpty()) {
                                currentPage++
                                currentVacancies = currentVacancies + newItems
                                vacancyChannel.send(newItems)
                                renderState(
                                    SearchScreenState.Content(
                                        currentVacancies,
                                        totalFound,
                                        isLoadingMore = false
                                    )
                                )
                            }
                        }

                        is Resource.Error -> {
                            renderState(SearchScreenState.Content(currentVacancies, totalFound, isLoadingMore = false))
                        }
                    }
                }
        }
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            currentSearchJob?.cancel()
            currentPaginationJob?.cancel()
            isNextPageLoading = false

            renderState(SearchScreenState.Loading)
            searchText = newSearchText
            currentPage = 1
            currentVacancies = emptyList()
            totalFound = 0

            currentSearchJob = viewModelScope.launch {
                searchInteractor
                    .searchVacancies(newSearchText, 1)
                    .collect { resource ->
                        when (resource) {
                            is Resource.Success -> {
                                if (resource.data?.items == null || resource.data.found == 0) {
                                    renderState(SearchScreenState.EmptyError)
                                } else {
                                    totalPages = resource.data.pages
                                    totalFound = resource.data.found
                                    currentVacancies = resource.data.items
                                    renderState(
                                        SearchScreenState.Content(
                                            resource.data.items,
                                            resource.data.found,
                                            isLoadingMore = false
                                        )
                                    )
                                }
                            }

                            is Resource.Error -> renderState(SearchScreenState.NetworkError)
                        }
                    }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentSearchJob?.cancel()
        currentPaginationJob?.cancel()
    }

    private fun renderState(state: SearchScreenState) {
        stateLiveData.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 1200L
    }
}
