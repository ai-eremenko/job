package ru.practicum.android.diploma.data.sharing

import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.sharing.SharingRepository
import ru.practicum.android.diploma.domain.util.ResourcesProviderRepository

class SharingRepositoryImpl(
    val resourcesProvider: ResourcesProviderRepository
) : SharingRepository {
    override fun shareVacancy(vacancyId: String, vacancyName: String): Intent {
        val shareText = getMessage(vacancyId, vacancyName)
        return Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
    }

    override fun sendOnEmail(email: String): Intent {
        return Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }
    }

    override fun call(phoneNumber: String): Intent {
        return Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

    private fun getMessage(vacancyId: String, vacancyName: String): String {
        return resourcesProvider.getString(R.string.share_vacancy)
    }
}
