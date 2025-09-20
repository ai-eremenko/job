package ru.practicum.android.diploma.presentation.industrychoice

import ru.practicum.android.diploma.domain.industrychoice.models.Industry

sealed class IndustryChoiceScreenState {
    object Empty : IndustryChoiceScreenState()
    object Error : IndustryChoiceScreenState()
    data class Content(
        val list: List<Industry>,
    ) : IndustryChoiceScreenState()

}
