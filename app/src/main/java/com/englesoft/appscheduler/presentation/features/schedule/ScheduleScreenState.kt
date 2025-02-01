package com.englesoft.appscheduler.presentation.features.schedule

import com.englesoft.appscheduler.domain.model.AppInfo

data class ScheduleScreenState(
    val isLoading: Boolean = false,
    val apps: List<AppInfo> = emptyList(),
    val error: String? = null
)
