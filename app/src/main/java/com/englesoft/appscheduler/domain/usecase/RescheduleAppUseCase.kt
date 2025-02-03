package com.englesoft.appscheduler.domain.usecase

import android.content.Context
import com.englesoft.appscheduler.domain.model.ScheduleInfo
import com.englesoft.appscheduler.domain.repository.ScheduleRepository
import com.englesoft.appscheduler.utils.AppUtils

class RescheduleAppUseCase(
    private val repository: ScheduleRepository,
    private val context: Context
) {
    suspend operator fun invoke(scheduleInfo: ScheduleInfo) {
        // Update database
        repository.updateSchedule(scheduleInfo)
        // Cancel existing alarm
        AppUtils.cancelSchedule(context, scheduleInfo.packageName)
        // Schedule new alarm
        AppUtils.scheduleApp(context, scheduleInfo.packageName, scheduleInfo.triggerTime)
    }
}