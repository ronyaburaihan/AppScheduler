package com.englesoft.appscheduler.domain.usecase

import com.englesoft.appscheduler.domain.model.ScheduleInfo
import com.englesoft.appscheduler.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetScheduleHistoryUseCase(
    private val scheduleRepository: ScheduleRepository
) {
    operator fun invoke(timestamp: Long): Flow<List<ScheduleInfo>> {
        return scheduleRepository.getScheduleHistory(timestamp)
    }

}