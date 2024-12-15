package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.FavouriteVacancyRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavouriteVacancyRepository

val repositoryModule = module {
    single<FavouriteVacancyRepository> {
        FavouriteVacancyRepositoryImpl(get(), get())
    }
}
