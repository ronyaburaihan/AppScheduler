package com.englesoft.appscheduler.di

import com.englesoft.appscheduler.domain.usecase.GetInstalledAppsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetInstalledAppsUseCase(get()) }
}