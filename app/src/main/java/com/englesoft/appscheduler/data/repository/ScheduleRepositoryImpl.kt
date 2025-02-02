package com.englesoft.appscheduler.data.repository

import com.englesoft.appscheduler.data.local.dao.ScheduleDao
import com.englesoft.appscheduler.domain.model.ScheduleInfo
import com.englesoft.appscheduler.domain.repository.ScheduleRepository

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

    override suspend fun getSchedules(): List<ScheduleInfo> {
        return dao.getSchedules().map { it.toScheduleInfo() }
    }

    override suspend fun getSchedule(packageName: String): ScheduleInfo? {
        return dao.getSchedule(packageName)?.toScheduleInfo()
    }

}