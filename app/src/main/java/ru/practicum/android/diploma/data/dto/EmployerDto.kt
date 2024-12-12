package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param name Название компании
 * @param logoUrls `Nullable` хранит экземпляр класса `LogoUrlsDto`, в котором хранятся ссылки на логотип компании
 */

data class EmployerDto(
    @SerializedName("name")val name: String,
    @SerializedName("logo_urls")val logoUrls: LogoUrlsDto?,
)
