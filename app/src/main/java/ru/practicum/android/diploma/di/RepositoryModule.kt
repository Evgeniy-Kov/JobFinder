package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.data.repository.VacancySearchRepositoryImpl
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.api.VacancySearchRepository

val repositoryModule = module {

    single<VacancySearchRepository> {
        VacancySearchRepositoryImpl(get())
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get())
    }

}
