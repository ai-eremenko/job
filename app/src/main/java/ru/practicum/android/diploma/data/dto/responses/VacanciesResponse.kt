package ru.practicum.android.diploma.data.dto.responses

import ru.practicum.android.diploma.data.dto.VacancyDto

class VacanciesResponse(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyPreviewDto>
) : Response()
