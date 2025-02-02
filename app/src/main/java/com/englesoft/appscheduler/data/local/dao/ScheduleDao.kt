package com.englesoft.appscheduler.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.englesoft.appscheduler.data.model.ScheduleEntity

@Dao
interface ScheduleDao {
    @Insert
    suspend fun insert(schedule: ScheduleEntity)

    @Query("DELETE FROM schedules WHERE packageName = :packageName")
    suspend fun delete(packageName: String)

    @Update
    suspend fun update(schedule: ScheduleEntity)

    @Query("SELECT * FROM schedules")
    suspend fun getSchedules(): List<ScheduleEntity>

    @Query("SELECT * FROM schedules WHERE packageName = :packageName")
    suspend fun getSchedule(packageName: String): ScheduleEntity?
}