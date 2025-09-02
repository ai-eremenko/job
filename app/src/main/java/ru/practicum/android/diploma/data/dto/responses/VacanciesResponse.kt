package ru.practicum.android.diploma.data.dto.responses

import ru.practicum.android.diploma.data.dto.VacancyDto

class VacanciesResponse(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyDto> //Изменю dto на VacancyPreview Этот Dto оставлю для запроса вакансии по id
) : Response()
