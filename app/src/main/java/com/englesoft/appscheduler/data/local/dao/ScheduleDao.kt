package com.englesoft.appscheduler.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.englesoft.appscheduler.data.model.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(schedule: ScheduleEntity)

    @Query("DELETE FROM schedules WHERE packageName = :packageName")
    suspend fun delete(packageName: String)

    @Update
    suspend fun update(schedule: ScheduleEntity)

    @Query("SELECT * FROM schedules WHERE executed = 0")
    fun getSchedules(): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM schedules WHERE executed = 1")
    fun getScheduleHistories(): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM schedules WHERE packageName = :packageName")
    suspend fun getSchedule(packageName: String): ScheduleEntity?
}