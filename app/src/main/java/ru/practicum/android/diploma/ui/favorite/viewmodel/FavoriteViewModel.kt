package ru.practicum.android.diploma.ui.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.mappers.FavoriteVacancyMapper
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.ui.favorite.state.FavoriteState

class FavoriteViewModel(
    private val favoriteInteractor: FavoriteInteractor,
    private val mapper: FavoriteVacancyMapper
): ViewModel() {

    private val _state = MutableLiveData<FavoriteState>()
    val state: LiveData<FavoriteState> = _state

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            favoriteInteractor.getFavorite()
                .map { vacancies -> mapper.mapToPreviewList(vacancies) }
                .collect { vacancies ->
                    _state.value = if (vacancies.isEmpty()) {
                        FavoriteState.Empty
                    } else {
                        FavoriteState.Content(vacancies)
                    }
                }
        }
    }

    fun refreshFavorites() {
        loadFavorites()
    }
}
