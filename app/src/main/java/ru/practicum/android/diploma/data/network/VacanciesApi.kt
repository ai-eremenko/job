package ru.practicum.android.diploma.data.network


import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.FilterAreaDto
import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.responses.VacanciesResponse

interface VacanciesApi {

    @GET("/industries")
    suspend fun getIndustries(
        @Header("Authorization") token: String
    ): List<FilterIndustryDto>

    @GET("/areas")
    suspend fun getAreas(
        @Header("Authorization") token: String
    ): List<FilterAreaDto>

    @GET("/vacancies")
    suspend fun getVacancies(
        @Header("Authorization") token: String,
        @Query("text") expression: String,
        @Query("page") page: Int
    ): VacanciesResponse
}
