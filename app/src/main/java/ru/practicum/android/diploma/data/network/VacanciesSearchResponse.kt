package ru.practicum.android.diploma.data.network

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.VacancyDto

/**
 * @param found  Обязательное поле, хранит количество найденных вакансий
 * @param items Обязательное поле, хранит массив вакансий
 * @param page Обязательное поле, хранит номер текущей страницы
 * @param pages Обязательное поле, хранит количество страниц
 * @param perPage Обязательное поле, хранит количество элементов на странице
 */

data class VacanciesSearchResponse(
    @SerializedName("found")val found: Int,
    @SerializedName("items") val items: List<VacancyDto>,
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("per_page") val perPage: Int,
) : Response()
