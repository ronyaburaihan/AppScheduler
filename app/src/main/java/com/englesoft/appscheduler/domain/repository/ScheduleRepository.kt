package com.englesoft.appscheduler.domain.repository

import com.englesoft.appscheduler.domain.model.ScheduleInfo
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun addSchedule(schedule: ScheduleInfo)
    suspend fun removeSchedule(packageName: String)
    suspend fun updateSchedule(schedule: ScheduleInfo)
    fun getSchedules(): Flow<List<ScheduleInfo>>
    fun getScheduleHistories(): Flow<List<ScheduleInfo>>
    suspend fun markAsExecuted(timeStamp: Long)
    suspend fun getSchedule(packageName: String): ScheduleInfo?
}