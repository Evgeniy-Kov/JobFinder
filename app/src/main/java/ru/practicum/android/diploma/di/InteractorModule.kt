package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.AreasInteractor
import ru.practicum.android.diploma.domain.api.FavouriteVacancyInteractor
import ru.practicum.android.diploma.domain.api.IndustriesInteractor
import ru.practicum.android.diploma.domain.api.PagingSourceInteractor
import ru.practicum.android.diploma.domain.api.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.VacancySearchInteractor
import ru.practicum.android.diploma.domain.impl.AreasInteractorImpl
import ru.practicum.android.diploma.domain.impl.FavouriteVacancyInteractorImpl
import ru.practicum.android.diploma.domain.impl.IndustriesInteractorImpl
import ru.practicum.android.diploma.domain.impl.PagingSourceInteractorImpl
import ru.practicum.android.diploma.domain.impl.SharedPreferencesInteractorImpl
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

    single<PagingSourceInteractor> {
        PagingSourceInteractorImpl(get())
    }

    single<IndustriesInteractor> {
        IndustriesInteractorImpl(get())
    }

    single<SharedPreferencesInteractor> {
        SharedPreferencesInteractorImpl(get())
    }

    factory<AreasInteractor> {
        AreasInteractorImpl(get())
    }
}
