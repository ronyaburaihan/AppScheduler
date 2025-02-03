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

    override fun getSchedules(): Flow<List<ScheduleInfo>> {
        return dao.getSchedules().map { schedules ->
            schedules.map { it.toScheduleInfo() }
        }
    }

    override fun getScheduleHistories(): Flow<List<ScheduleInfo>> {
        return dao.getScheduleHistories().map { schedules ->
            schedules.map { it.toScheduleInfo() }
        }
    }

    override suspend fun markAsExecuted(timeStamp: Long) {
        dao.markAsExecuted(timeStamp)
    }

    override suspend fun getSchedule(packageName: String): ScheduleInfo? {
        return dao.getSchedule(packageName)?.toScheduleInfo()
    }

}