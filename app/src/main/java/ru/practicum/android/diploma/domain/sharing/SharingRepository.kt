package ru.practicum.android.diploma.domain.sharing

import android.content.Intent

interface SharingRepository {
    fun shareVacancy(vacancyLink: String, vacancyName: String): Intent
    fun sendOnEmail(email: String): Intent
    fun call(phoneNumber: String): Intent
}
