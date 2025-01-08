package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.Filter

fun parseFilter(filter: Filter): Map<String, String> {
    val result = mutableMapOf<String, String>()
    if (filter.country != null) result["area"] = filter.country.id

    if (filter.region != null) result["area"] = filter.region.id

    if (filter.industry != null) result["industry"] = filter.industry.id

    if (filter.salary != null) result["salary"] = filter.salary.toString()

    if (filter.onlyWithSalary) result["only_with_salary"] = "true"

    return result
}
