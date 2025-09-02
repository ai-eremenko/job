package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.FilterIndustryDto

interface VacanciesApi {

    @GET("/industries")
    suspend fun getIndustries(
        @Header("Authorization") token: String
    ): List<FilterIndustryDto>

    @GET("/areas")
    suspend fun getAreas(
        @Header("Authorization") token: String
    ): List<FilterAreaDto>

    // еще запросы
}
