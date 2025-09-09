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
            processParagraph(context, paragraph, spannable)
        }
    }

    return spannable
}

private fun processParagraph(context: Context, paragraph: String, spannable: SpannableStringBuilder) {
    val trimmedParagraph = paragraph.trim()

    when {
        trimmedParagraph.endsWith(":") -> appendHeader(context, trimmedParagraph, spannable)
        isBulletList(trimmedParagraph) -> appendBulletList(context, trimmedParagraph, spannable)
        else -> appendRegularText(context, trimmedParagraph, spannable)
    }
}

private fun appendHeader(context: Context, text: String, spannable: SpannableStringBuilder) {
    val header = SpannableString("$text\n\n")
    header.setSpan(
        TextAppearanceSpan(context, R.style.TextMedium_16),
        0,
        header.length,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.append(header)
}

private fun appendBulletList(context: Context, text: String, spannable: SpannableStringBuilder) {
    val lines = text.split("\n")
    for (line in lines) {
        appendBulletItem(context, line, spannable)
    }
    spannable.append("\n")
}

private fun appendBulletItem(context: Context, line: String, spannable: SpannableStringBuilder) {
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

private fun appendRegularText(context: Context, text: String, spannable: SpannableStringBuilder) {
    if (spannable.isNotEmpty()) {
        spannable.append("\n")
    }
    val regularText = SpannableString("$text\n\n")
    regularText.setSpan(
        TextAppearanceSpan(context, R.style.TextRegular_16),
        0,
        regularText.length,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannable.append(regularText)
}

private fun isBulletList(text: String): Boolean {
    val lines = text.split("\n")
    return lines.any { line ->
        val trimmedLine = line.trim()
        trimmedLine.startsWith("-") || trimmedLine.startsWith("•")
    }
}
