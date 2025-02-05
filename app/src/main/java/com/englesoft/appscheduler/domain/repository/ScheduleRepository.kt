package com.englesoft.appscheduler.domain.repository

import com.englesoft.appscheduler.domain.model.ScheduleInfo
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun addSchedule(schedule: ScheduleInfo)
    suspend fun removeSchedule(packageName: String)
    suspend fun updateSchedule(schedule: ScheduleInfo)
    fun getSchedules(timestamp: Long): Flow<List<ScheduleInfo>>
    fun getScheduleHistory(timestamp: Long): Flow<List<ScheduleInfo>>
    suspend fun markAsExecuted(timestamp: Long)
    suspend fun getSchedule(timestamp: Long): ScheduleInfo?
}