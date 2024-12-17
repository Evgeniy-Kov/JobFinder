package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.search.VacancySearchViewModel

val viewModelModule = module {

    viewModel {
        VacancySearchViewModel(get(), get())
    }

}
