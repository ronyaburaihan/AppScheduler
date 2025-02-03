package com.englesoft.appscheduler.di

import com.englesoft.appscheduler.domain.usecase.GetInstalledAppsUseCase
import com.englesoft.appscheduler.domain.usecase.GetSchedulesUseCase
import com.englesoft.appscheduler.domain.usecase.ScheduleAppUseCase
import com.englesoft.appscheduler.domain.usecase.CancelScheduleUseCase
import com.englesoft.appscheduler.domain.usecase.RescheduleAppUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetInstalledAppsUseCase(get()) }
    single { ScheduleAppUseCase(get(), get()) }
    single { GetSchedulesUseCase(get()) }
    single { CancelScheduleUseCase(get(), get()) }
    single { RescheduleAppUseCase(get(), get()) }
}