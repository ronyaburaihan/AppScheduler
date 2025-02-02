package com.englesoft.appscheduler.di

import com.englesoft.appscheduler.presentation.features.home.HomeViewModel
import com.englesoft.appscheduler.presentation.features.schedule.ScheduleViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { ScheduleViewModel(get(), get()) }
}