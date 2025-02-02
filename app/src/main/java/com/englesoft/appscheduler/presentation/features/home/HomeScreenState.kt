package com.englesoft.appscheduler.presentation.features.home

import com.englesoft.appscheduler.domain.model.ScheduleInfo

data class HomeScreenState(
    val schedules: List<ScheduleInfo> = emptyList(),
    val scheduleHistories: List<ScheduleInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
