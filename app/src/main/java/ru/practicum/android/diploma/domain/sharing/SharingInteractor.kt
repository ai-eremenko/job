package ru.practicum.android.diploma.domain.sharing

import android.content.Intent
import ru.practicum.android.diploma.domain.sharing.models.SharingIntent

interface SharingInteractor {
    fun shareVacancy(vacancyId: String, vacancyName: String): SharingIntent
    fun sendOnEmail(email: String): SharingIntent
    fun call(phone: String): SharingIntent
}
