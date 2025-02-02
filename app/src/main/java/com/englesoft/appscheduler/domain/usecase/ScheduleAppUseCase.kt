package com.englesoft.appscheduler.domain.usecase

import com.englesoft.appscheduler.domain.model.ScheduleInfo
import com.englesoft.appscheduler.domain.repository.ScheduleRepository

class ScheduleAppUseCase(private val repository: ScheduleRepository) {
    suspend operator fun invoke(schedule: ScheduleInfo) {
        repository.addSchedule(schedule)
    }
}