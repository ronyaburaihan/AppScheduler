package com.englesoft.appscheduler.presentation.features.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.englesoft.appscheduler.domain.model.ScheduleInfo
import com.englesoft.appscheduler.domain.usecase.CancelScheduleUseCase
import com.englesoft.appscheduler.domain.usecase.GetScheduleHistoryUseCase
import com.englesoft.appscheduler.domain.usecase.GetSchedulesUseCase
import com.englesoft.appscheduler.domain.usecase.RescheduleAppUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getSchedulesUseCase: GetSchedulesUseCase,
    private val rescheduleAppUseCase: RescheduleAppUseCase,
    private val cancelScheduleUseCase: CancelScheduleUseCase,
    private val getScheduleHistoryUseCase: GetScheduleHistoryUseCase
) : ViewModel() {

    var screenState by mutableStateOf(HomeScreenState())
        private set

    init {
        getSchedules()
        getScheduleHistory()
    }

    private fun getSchedules() {
        viewModelScope.launch {
            getSchedulesUseCase(System.currentTimeMillis()).collectLatest { schedules ->
                screenState = screenState.copy(
                    schedules = schedules
                )
            }
        }
    }

    private fun getScheduleHistory() {
        viewModelScope.launch {
            getScheduleHistoryUseCase(System.currentTimeMillis()).collectLatest { schedules ->
                screenState = screenState.copy(
                    scheduleHistory = schedules
                )
            }
        }
    }

    fun cancelSchedule(packageName: String) {
        viewModelScope.launch {
            cancelScheduleUseCase(packageName)
        }
    }

    fun rescheduleApp(scheduleInfo: ScheduleInfo) {
        viewModelScope.launch {
            rescheduleAppUseCase(scheduleInfo)
        }
    }
}