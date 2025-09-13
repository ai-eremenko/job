package ru.practicum.android.diploma.presentation.filteringsettings.models

import ru.practicum.android.diploma.domain.filteringsettings.models.FilterSettings

sealed class FilterScreenState {
    object Empty : FilterScreenState()
    data class Content(
        val filter: FilterSettings
    ) : FilterScreenState()
}
