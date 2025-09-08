package ru.practicum.android.diploma.presentation.search

import ru.practicum.android.diploma.domain.search.models.VacancyPreviewPresent

sealed interface SearchScreenState {

    object Empty : SearchScreenState
    object Loading : SearchScreenState
    object NetworkError : SearchScreenState
    object EmptyError : SearchScreenState

    data class Content(
        val vacancy: List<VacancyPreviewPresent>,
        val countVacancy: Int
    ) : SearchScreenState
}
