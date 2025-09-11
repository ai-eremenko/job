package ru.practicum.android.diploma.domain.sharing

import android.content.Intent

interface SharingRepository {
    fun shareVacancy(vacancyId: String, vacancyName: String): Intent
    fun sendOnEmail(email: String): Intent
    fun call(phone: String): Intent
}
