package com.englesoft.appscheduler.di

import com.englesoft.appscheduler.domain.usecase.GetInstalledAppsUseCase
import com.englesoft.appscheduler.domain.usecase.ScheduleAppUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetInstalledAppsUseCase(get()) }
    single { ScheduleAppUseCase(get()) }
}