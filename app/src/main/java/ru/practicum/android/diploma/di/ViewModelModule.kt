package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.favourites.FavoritesViewModel
import ru.practicum.android.diploma.ui.search.VacancySearchViewModel
import ru.practicum.android.diploma.ui.vacancy.VacancyDetailsViewModel

val viewModelModule = module {

    viewModel { FavoritesViewModel(get()) }

    viewModel {
        VacancySearchViewModel(get())
    }

    viewModel {
        VacancyDetailsViewModel(get())
    }
}
