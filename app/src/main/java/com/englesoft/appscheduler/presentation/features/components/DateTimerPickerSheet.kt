package com.englesoft.appscheduler.presentation.features.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.englesoft.appscheduler.presentation.theme.PaddingExtraSmall
import com.englesoft.appscheduler.presentation.theme.PaddingMedium
import com.englesoft.appscheduler.presentation.theme.PaddingSmall
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerSheet(
    appName: String,
    initialDate: Long? = null,
    onSchedule: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val todayMillis = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
    val selectedCalendar = Calendar.getInstance().apply {
        if (initialDate != null) {
            timeInMillis = initialDate
        }
    }
    val selectedTimeValid = remember { mutableStateOf(true) }

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= todayMillis
            }
        }
    ).apply {
        if (initialDate != null) {
            selectedDateMillis = initialDate
        }
    }

    val timePickerState = rememberTimePickerState(
        initialHour = selectedCalendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = selectedCalendar.get(Calendar.MINUTE),
        is24Hour = false,
    )

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(PaddingSmall)
        ) {
            Text(
                text = "Schedule $appName app",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = PaddingExtraSmall)
            )
            Text(
                text = "Choose a date and time to launch the app:",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = PaddingSmall)
            )

            DatePicker(
                modifier = Modifier.fillMaxWidth(),
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                )
            )

            datePickerState.selectedDateMillis?.let {

                TimePicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = PaddingMedium
                        ),
                    state = timePickerState
                )

                selectedCalendar.apply {
                    timeInMillis = it
                    set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    set(Calendar.MINUTE, timePickerState.minute)
                }

                selectedTimeValid.value =
                    selectedCalendar.timeInMillis >= System.currentTimeMillis()

                if (!selectedTimeValid.value) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = PaddingExtraSmall),
                        text = "Selected time must be in the future!",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = PaddingSmall,
                        start = PaddingSmall,
                        end = PaddingSmall
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onDismiss
                ) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.size(PaddingSmall))
                Button(
                    modifier = Modifier.weight(1f),
                    enabled = datePickerState.selectedDateMillis != null && selectedTimeValid.value,
                    onClick = { onSchedule(selectedCalendar.timeInMillis) }
                ) {
                    Text("Schedule")
                }
            }
        }
    }
}