package ru.practicum.android.diploma.domain.sharing.impl

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.sharing.SharingRepository
import ru.practicum.android.diploma.domain.sharing.models.SharingIntent
import ru.practicum.android.diploma.domain.util.ResourcesProviderRepository

class SharingInteractorImpl(
    val repository: SharingRepository,
    val resourcesProvider: ResourcesProviderRepository
) : SharingInteractor {
    override fun shareVacancy(
        vacancyId: String,
        vacancyName: String
    ): SharingIntent {
        val error = resourcesProvider.getString(R.string.error_share)
        return SharingIntent(repository.sendOnEmail(vacancyName), error)
    }

    override fun sendOnEmail(email: String): SharingIntent {
        val error = resourcesProvider.getString(R.string.error_send_email)
        return SharingIntent(repository.sendOnEmail(email), error)
    }

    override fun call(phone: String): SharingIntent {
        val error = resourcesProvider.getString(R.string.error_phone)
        return SharingIntent(repository.call(phone), error)
    }
}
