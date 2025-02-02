package com.englesoft.appscheduler.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.englesoft.appscheduler.data.local.dao.ScheduleDao
import com.englesoft.appscheduler.data.model.ScheduleEntity

@Database(entities = [ScheduleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
}