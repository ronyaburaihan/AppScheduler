package com.englesoft.appscheduler.domain.usecase

import android.content.Context
import com.englesoft.appscheduler.domain.repository.ScheduleRepository
import com.englesoft.appscheduler.utils.AppUtils

class CancelScheduleUseCase(
    private val repository: ScheduleRepository,
    private val context: Context
) {
    suspend operator fun invoke(packageName: String) {
        // Cancel existing alarm
        AppUtils.cancelSchedule(context, packageName)
        // Remove from database
        repository.removeSchedule(packageName)
    }
}