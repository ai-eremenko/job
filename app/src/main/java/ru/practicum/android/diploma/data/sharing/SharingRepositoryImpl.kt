package ru.practicum.android.diploma.data.sharing

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.sharing.SharingRepository
import ru.practicum.android.diploma.domain.sharing.models.EmailData
import ru.practicum.android.diploma.domain.sharing.models.PhoneData
import ru.practicum.android.diploma.domain.sharing.models.ShareData
import ru.practicum.android.diploma.domain.util.ResourcesProviderRepository

class SharingRepositoryImpl(
    val resourcesProvider: ResourcesProviderRepository
) : SharingRepository {
    override fun shareVacancy(vacancyLink: String, vacancyName: String): ShareData {
        val shareText = getMessage(vacancyLink, vacancyName)
        return ShareData(shareText)
    }

    override fun sendOnEmail(email: String): EmailData {
        return EmailData(email)
    }

    override fun call(phoneNumber: String): PhoneData {
        return PhoneData(phoneNumber)
    }

    private fun getMessage(vacancyLink: String, vacancyName: String): String {
        return resourcesProvider.getString(R.string.share_vacancy, vacancyName, vacancyLink)
    }
}
