package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.AreasDto
import ru.practicum.android.diploma.data.dto.CountriesDto
import ru.practicum.android.diploma.data.dto.DictionaryDto
import ru.practicum.android.diploma.data.dto.IndustriesDto
import ru.practicum.android.diploma.data.dto.VacancyDetailsDto

interface HhApi {
    @Headers(
        "Authorization: Bearer $TOKEN",
        "HH-User-Agent: $APPLICATION_NAME ($EMAIL)"
    )
    @GET("/vacancies?per_page=20")
    suspend fun getVacancies(
        @Query("text") searchRequest: String,
        @Query("page") pageNumber: Int,
        @QueryMap options: Map<String, String>
    ): VacanciesSearchResponse

    @Headers(
        "Authorization: Bearer $TOKEN",
        "HH-User-Agent: $APPLICATION_NAME ($EMAIL)"
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(@Path("vacancy_id") vacancyId: String): VacancyDetailsDto

    @GET("/industries")
    suspend fun getIndustries(): IndustriesDto

    @GET("/areas/countries")
    suspend fun getCountries(): CountriesDto

    @GET("/areas")
    suspend fun getAreas(): AreasDto

    @GET("/dictionaries")
    suspend fun getDictionaries(): DictionaryDto

    private companion object {
        const val APPLICATION_NAME = "JobFinder"
        const val EMAIL = "twenty6@duck.com"
        const val TOKEN = BuildConfig.HH_ACCESS_TOKEN
    }
}
