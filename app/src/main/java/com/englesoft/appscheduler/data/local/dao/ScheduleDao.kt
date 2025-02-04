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

    @Query("SELECT * FROM schedules WHERE executed = 0 AND triggerTime > :timestamp")
    fun getSchedules(timestamp: Long): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM schedules WHERE executed = 1 OR triggerTime < :timestamp ORDER BY triggerTime DESC")
    fun getScheduleHistory(timestamp: Long): Flow<List<ScheduleEntity>>

    @Query("UPDATE schedules SET executed = 1 WHERE triggerTime = :timestamp")
    suspend fun markAsExecuted(timestamp: Long)

    @Query("SELECT * FROM schedules WHERE packageName = :packageName")
    suspend fun getSchedule(packageName: String): ScheduleEntity?
}