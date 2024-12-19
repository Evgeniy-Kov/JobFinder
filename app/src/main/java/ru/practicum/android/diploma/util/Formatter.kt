package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.SalaryDto

fun getFormattedSalary(salary: SalaryDto?, context: Context): String {
    return if (salary != null) {
        var result = ""
        val from = salary.from
        val to = salary.to
        if (from != null) {
            result += String.format(context.getString(R.string.salary_from), from)
        }
        if (to != null) {
            result += String.format(context.getString(R.string.salary_to), to)
        }
        result
    } else {
        context.getString(R.string.salary_not_specified)
    }
}

fun getFormattedSalaryForViewHolder(salaryFrom: Int?, salaryTo: Int?, context: Context): String {
    if (salaryFrom == null && salaryTo == null) {
        return context.getString(R.string.salary_not_specified)
    } else {
        var result = ""
        if (salaryFrom != null) {
            result += String.format(context.getString(R.string.salary_from), salaryFrom)
        }
        if (salaryTo != null) {
            result += " " + String.format(context.getString(R.string.salary_to), salaryTo)
        }
        return result
    }
}

fun getVacancyNameForViewHolder(vacancyName: String, city: String): String {
    return vacancyName + ", " + city
}
