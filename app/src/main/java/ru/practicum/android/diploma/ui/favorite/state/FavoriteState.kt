package ru.practicum.android.diploma.ui.favorite.state

import ru.practicum.android.diploma.domain.search.models.VacancyPreviewPresent

sealed class FavoriteState {
    object Empty : FavoriteState()
    data class Content(val vacancies: List<VacancyPreviewPresent>) : FavoriteState()
}
