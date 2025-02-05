package com.englesoft.appscheduler.domain.usecase

import android.content.Context
import com.englesoft.appscheduler.domain.model.ScheduleInfo
import com.englesoft.appscheduler.domain.repository.ScheduleRepository
import com.englesoft.appscheduler.utils.AppUtils

class ScheduleAppUseCase(
    private val repository: ScheduleRepository,
    private val context: Context
) {
    suspend operator fun invoke(schedule: ScheduleInfo): String? {
        val existingSchedule = repository.getSchedule(schedule.triggerTime)

        if (existingSchedule != null) {
            return "${existingSchedule.name} is already scheduled at this time!"
        }

        repository.addSchedule(schedule)
        AppUtils.scheduleApp(context, schedule.packageName, schedule.triggerTime)
        return null
    }
}