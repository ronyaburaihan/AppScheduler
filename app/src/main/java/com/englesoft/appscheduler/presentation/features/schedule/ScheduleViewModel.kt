package com.englesoft.appscheduler.presentation.features.schedule

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.englesoft.appscheduler.domain.model.AppInfo
import com.englesoft.appscheduler.domain.model.ScheduleInfo
import com.englesoft.appscheduler.domain.usecase.GetInstalledAppsUseCase
import com.englesoft.appscheduler.domain.usecase.ScheduleAppUseCase
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val scheduleAppUseCase: ScheduleAppUseCase
) : ViewModel() {

    var state = mutableStateOf(ScheduleScreenState())
        private set

    init {
        loadAllApps()
    }

    private fun loadAllApps() {
        val apps = getInstalledAppsUseCase()

        state.value = state.value.copy(
            isLoading = false,
            apps = apps,
            error = null
        )
    }

    fun scheduleApp(appInfo: AppInfo, triggerTime: Long) {
        state.value = state.value.copy(
            error = null
        )
        val scheduleInfo = ScheduleInfo(
            packageName = appInfo.packageName,
            name = appInfo.name,
            icon = appInfo.icon,
            triggerTime = triggerTime
        )
        viewModelScope.launch {
            val result = scheduleAppUseCase(scheduleInfo)
            state.value = state.value.copy(
                isScheduleSuccess = result == null,
                error = result
            )
        }
    }

    fun clearErrorMessages() {
        state.value = state.value.copy(
            error = null
        )
    }
}