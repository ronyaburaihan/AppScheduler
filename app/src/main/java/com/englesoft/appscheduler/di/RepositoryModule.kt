package com.englesoft.appscheduler.di

import com.englesoft.appscheduler.data.repository.ScheduleRepositoryImpl
import com.englesoft.appscheduler.domain.repository.ScheduleRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ScheduleRepository> { ScheduleRepositoryImpl(get()) }
}