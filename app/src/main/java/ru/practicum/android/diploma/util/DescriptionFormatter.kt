package ru.practicum.android.diploma.util

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.TextAppearanceSpan
import ru.practicum.android.diploma.R

fun formatDescription(context: Context, description: String): SpannableStringBuilder {
    val spannable = SpannableStringBuilder()
    val lines = description.split("\n\n")

    for (line in lines) {
        if (line.isNotBlank()) {
            if (line.contains(":")) {
                val header = SpannableString("$line\n\n")
                header.setSpan(
                    TextAppearanceSpan(context, R.style.TextMedium_16),
                    0,
                    header.length,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannable.append(header)
            } else {
                val items = line.split("\n")
                for (item in items) {
                    if (item.isNotBlank()) {
                        val listItem = "•\t${item.trim()}\n"
                        val spannableItem = SpannableString(listItem)
                        spannableItem.setSpan(
                            TextAppearanceSpan(context, R.style.TextRegular_16),
                            0,
                            listItem.length,
                            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        spannable.append(spannableItem)
                    }
                }
                spannable.append("\n")
            }
        }
    }

    return spannable
}
