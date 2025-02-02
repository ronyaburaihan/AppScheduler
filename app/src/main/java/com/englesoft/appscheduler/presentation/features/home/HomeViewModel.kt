package com.englesoft.appscheduler.presentation.features.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.englesoft.appscheduler.domain.usecase.GetSchedulesUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getSchedulesUseCase: GetSchedulesUseCase
) : ViewModel() {

    var screenState by mutableStateOf(HomeScreenState())
        private set

    init {
        getSchedules()
    }

    private fun getSchedules() {
        viewModelScope.launch {
            getSchedulesUseCase().collectLatest { schedules ->
                screenState = screenState.copy(
                    schedules = schedules
                )
            }
        }
    }
}