package ru.practicum.android.diploma.domain.sharing

import ru.practicum.android.diploma.domain.sharing.models.EmailData
import ru.practicum.android.diploma.domain.sharing.models.PhoneData
import ru.practicum.android.diploma.domain.sharing.models.ShareData

interface SharingInteractor {
    fun shareVacancy(vacancyId: String, vacancyName: String): Pair<ShareData, String>
    fun sendOnEmail(email: String): Pair<EmailData, String>
    fun call(phone: String): Pair<PhoneData, String>
}
