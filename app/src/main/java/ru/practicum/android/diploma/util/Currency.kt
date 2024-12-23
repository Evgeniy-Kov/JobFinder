package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.data.dto.CurrencyDto
import ru.practicum.android.diploma.data.dto.SalaryDto

fun getCurrencySymbol(salary: SalaryDto?, currencies: List<CurrencyDto>?): String? {
    return if (salary == null || salary.currency == null) {
        null
    } else {
        currencies?.find { it.code == salary.currency }?.abbr
    }
}
