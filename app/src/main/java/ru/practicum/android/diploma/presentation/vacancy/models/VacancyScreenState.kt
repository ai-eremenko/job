package ru.practicum.android.diploma.presentation.vacancy.models

import android.graphics.drawable.Drawable
import ru.practicum.android.diploma.domain.vacancy.models.VacancyPresent

sealed class VacancyScreenState {
    object Loading: VacancyScreenState()
    data class Content(
        val vacancyModel: VacancyPresent
    ): VacancyScreenState()

    data class Favorite(
        val favoriteIcon: Drawable?
    ) : VacancyScreenState()
    object ErrorNotFound: VacancyScreenState()
}
