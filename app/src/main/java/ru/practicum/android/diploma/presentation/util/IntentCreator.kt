package ru.practicum.android.diploma.presentation.util

import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.domain.sharing.models.EmailData
import ru.practicum.android.diploma.domain.sharing.models.PhoneData
import ru.practicum.android.diploma.domain.sharing.models.ShareData

object IntentCreator {
    fun createShareIntent(shareData: ShareData): Intent {
        return Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareData.text)
        }
    }

    fun createEmailIntent(emailData: EmailData): Intent {
        return Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailData.email))
        }
    }

    fun createPhoneIntent(phoneData: PhoneData): Intent {
        return Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${phoneData.phoneNumber}")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}
