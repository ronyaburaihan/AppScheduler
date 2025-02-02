package com.englesoft.appscheduler.domain.usecase

import com.englesoft.appscheduler.domain.model.ScheduleInfo
import com.englesoft.appscheduler.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetSchedulesUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(): Flow<List<ScheduleInfo>> {
        return repository.getSchedules()
    }
}