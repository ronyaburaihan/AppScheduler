package com.englesoft.appscheduler.presentation.features.schedule

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.englesoft.appscheduler.domain.usecase.GetInstalledAppsUseCase

class ScheduleViewModel(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
) : ViewModel() {

    var state = mutableStateOf(ScheduleScreenState())
        private set

    init {
        loadAllApps()
    }

    private fun loadAllApps() {
        state.value = state.value.copy(
            isLoading = true,
            error = null
        )

        val apps = getInstalledAppsUseCase()

        state.value = state.value.copy(
            isLoading = false,
            apps = apps,
            error = null
        )
    }
}