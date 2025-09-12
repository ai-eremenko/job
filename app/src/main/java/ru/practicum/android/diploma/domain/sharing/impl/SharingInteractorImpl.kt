package ru.practicum.android.diploma.domain.sharing.impl

import android.content.Intent
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.sharing.SharingRepository
import ru.practicum.android.diploma.domain.util.ResourcesProviderRepository

class SharingInteractorImpl(
    val repository: SharingRepository,
    val resourcesProvider: ResourcesProviderRepository
) : SharingInteractor {
    override fun shareVacancy(vacancyId: String, vacancyName: String): Pair<Intent, String> {
        val error = resourcesProvider.getString(R.string.error_share)
        val intent = repository.shareVacancy(vacancyId, vacancyName)
        return Pair(intent, error)
    }

    override fun sendOnEmail(email: String): Pair<Intent, String> {
        val error = resourcesProvider.getString(R.string.error_send_email)
        val intent = repository.sendOnEmail(email)
        return Pair(intent, error)
    }

    override fun call(phone: String): Pair<Intent, String> {
        val error = resourcesProvider.getString(R.string.error_phone)
        val intent = repository.call(phone)
        return Pair(intent, error)
    }
}
