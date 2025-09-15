package ru.practicum.android.diploma.util

fun skillsFormatter(skills: List<String>): String {
    return skills.joinToString(
        separator = "\n  •  ",
        prefix = "  •  ",
        transform = { it.trim() }
    )
}
