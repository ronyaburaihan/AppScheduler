package com.englesoft.appscheduler.data.repository

import com.englesoft.appscheduler.data.local.dao.ScheduleDao
import com.englesoft.appscheduler.domain.model.ScheduleInfo
import com.englesoft.appscheduler.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ScheduleRepositoryImpl(private val dao: ScheduleDao) : ScheduleRepository {

    override suspend fun addSchedule(schedule: ScheduleInfo) {
        dao.insert(schedule.toScheduleEntity())
    }

    override suspend fun removeSchedule(packageName: String) {
        dao.delete(packageName)
    }

    override suspend fun updateSchedule(schedule: ScheduleInfo) {
        dao.update(schedule.toScheduleEntity())
    }

    override fun getSchedules(timestamp: Long): Flow<List<ScheduleInfo>> {
        return dao.getSchedules(timestamp).map { schedules ->
            schedules.map { it.toScheduleInfo() }
        }
    }

    override fun getScheduleHistory(timestamp: Long): Flow<List<ScheduleInfo>> {
        return dao.getScheduleHistory(timestamp).map { schedules ->
            schedules.map { it.toScheduleInfo() }
        }
    }

    override suspend fun markAsExecuted(timestamp: Long) {
        dao.markAsExecuted(timestamp)
    }

    override suspend fun getSchedule(timestamp: Long): ScheduleInfo? {
        return dao.getSchedule(timestamp)?.toScheduleInfo()
    }

}