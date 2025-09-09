package ru.practicum.android.diploma.util

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.TextAppearanceSpan
import ru.practicum.android.diploma.R

fun formatDescription(context: Context, description: String): SpannableStringBuilder {
    val spannable = SpannableStringBuilder()
    val paragraphs = description.split("\n\n")

    for (paragraph in paragraphs) {
        if (paragraph.isNotBlank()) {
            val trimmedParagraph = paragraph.trim()

            if (trimmedParagraph.endsWith(":")) {
                val header = SpannableString("$trimmedParagraph\n\n")
                header.setSpan(
                    TextAppearanceSpan(context, R.style.TextMedium_16),
                    0,
                    header.length,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannable.append(header)
            } else if (isBulletList(trimmedParagraph)) {
                val lines = trimmedParagraph.split("\n")
                for (line in lines) {
                    val trimmedLine = line.trim()
                    if (trimmedLine.isNotBlank() && (trimmedLine.startsWith("-") || trimmedLine.startsWith("•"))) {
                        val cleanItem = trimmedLine.substring(1).trim()
                        val listItem = "•\t$cleanItem\n"
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
            } else {
                if (spannable.isNotEmpty()) {
                    spannable.append("\n")
                }
                val regularText = SpannableString("$trimmedParagraph\n\n")
                regularText.setSpan(
                    TextAppearanceSpan(context, R.style.TextRegular_16),
                    0,
                    regularText.length,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannable.append(regularText)
            }
        }
    }

    return spannable
}

private fun isBulletList(text: String): Boolean {
    val lines = text.split("\n")
    return lines.any { line ->
        val trimmedLine = line.trim()
        trimmedLine.startsWith("-") || trimmedLine.startsWith("•")
    }
}
