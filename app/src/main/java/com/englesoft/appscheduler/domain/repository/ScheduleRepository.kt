package com.englesoft.appscheduler.domain.repository

import com.englesoft.appscheduler.domain.model.ScheduleInfo

interface ScheduleRepository {
    suspend fun addSchedule(schedule: ScheduleInfo)
    suspend fun removeSchedule(packageName: String)
    suspend fun updateSchedule(schedule: ScheduleInfo)
    suspend fun getSchedules(): List<ScheduleInfo>
    suspend fun getSchedule(packageName: String): ScheduleInfo?
}