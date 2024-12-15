package ru.practicum.android.diploma.data.dto

/**
 * @param email `Nullable` Хранит электронную почту. Значение поля должно соответствовать формату email
 * @param name `Nullable` Хранит имя контакта
 * @param phones `Nullable` Хранит массив экземпляров класса `PhoneDto` с информацией о телефонных номерах
 */

data class ContactsDto(
    val email: String?,
    val name: String?,
    val phones: List<PhoneDto>,
)
