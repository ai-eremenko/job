package ru.practicum.android.diploma.domain.sharing

import android.content.Intent

interface SharingInteractor {
    fun shareVacancy(vacancyId: String, vacancyName: String): Pair<Intent, String>
    fun sendOnEmail(email: String): Pair<Intent, String>
    fun call(phone: String): Pair<Intent, String>
}
