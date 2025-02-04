package com.englesoft.appscheduler.presentation.features.home

import com.englesoft.appscheduler.domain.model.ScheduleInfo

data class HomeScreenState(
    val schedules: List<ScheduleInfo> = emptyList(),
    val scheduleHistory: List<ScheduleInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
