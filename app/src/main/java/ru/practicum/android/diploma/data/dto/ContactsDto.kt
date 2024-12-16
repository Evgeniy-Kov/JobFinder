package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @param email `Nullable` Хранит электронную почту. Значение поля должно соответствовать формату email
 * @param name `Nullable` Хранит имя контакта
 * @param phones `Nullable` Хранит массив экземпляров класса `PhoneDto` с информацией о телефонных номерах
 */

data class ContactsDto(
    @SerializedName("email") val email: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("phones") val phones: List<PhoneDto>,
)
