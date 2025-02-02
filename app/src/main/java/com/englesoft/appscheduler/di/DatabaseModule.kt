package com.englesoft.appscheduler.di

import androidx.room.Room
import com.englesoft.appscheduler.data.local.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "scheduler_db").build() }
    single { get<AppDatabase>().scheduleDao() }
}