package com.englesoft.appscheduler

import android.app.Application
import com.englesoft.appscheduler.di.useCaseModule
import com.englesoft.appscheduler.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AppsSchedulerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppsSchedulerApplication)
            modules(viewModelModule, useCaseModule)
        }
    }
}