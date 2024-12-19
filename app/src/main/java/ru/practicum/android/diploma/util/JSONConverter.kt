package ru.practicum.android.diploma.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun convertToJson(listToJson: List<String>): String {
    if (listToJson.isEmpty()) return ""
    return Gson().toJson(listToJson)
}

inline fun <reified T> convertFromJson(jsonToList: String?): List<T> {
    if (jsonToList == null || jsonToList.isEmpty()) return emptyList()
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(jsonToList, type)
}
