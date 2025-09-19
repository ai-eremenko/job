package ru.practicum.android.diploma.domain.sharing

import ru.practicum.android.diploma.domain.sharing.models.EmailData
import ru.practicum.android.diploma.domain.sharing.models.PhoneData
import ru.practicum.android.diploma.domain.sharing.models.ShareData

interface SharingRepository {
    fun shareVacancy(vacancyLink: String, vacancyName: String): ShareData
    fun sendOnEmail(email: String): EmailData
    fun call(phoneNumber: String): PhoneData
}
