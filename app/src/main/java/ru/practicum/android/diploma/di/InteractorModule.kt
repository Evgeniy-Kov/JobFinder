package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.FavouriteVacancyInteractor
import ru.practicum.android.diploma.domain.impl.FavouriteVacancyInteractorImpl

val interactorModule = module {
    single<FavouriteVacancyInteractor> {
        FavouriteVacancyInteractorImpl(get())
    }
}
