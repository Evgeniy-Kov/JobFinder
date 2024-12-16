package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.FavouriteVacancyInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.VacancySearchInteractor
import ru.practicum.android.diploma.domain.impl.FavouriteVacancyInteractorImpl
import ru.practicum.android.diploma.domain.impl.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.domain.impl.VacancySearchInteractorImpl

val interactorModule = module {
    single<FavouriteVacancyInteractor> {
        FavouriteVacancyInteractorImpl(get())
    }

    single<VacancySearchInteractor> {
        VacancySearchInteractorImpl(get())
    }

    single<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get())
    }

}
