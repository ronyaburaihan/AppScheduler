package com.englesoft.appscheduler.presentation.features.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toDrawable
import coil.compose.rememberAsyncImagePainter
import com.englesoft.appscheduler.R
import com.englesoft.appscheduler.domain.model.AppInfo
import com.englesoft.appscheduler.presentation.theme.CornerRadiusLarge
import com.englesoft.appscheduler.presentation.theme.PaddingExtraSmall
import com.englesoft.appscheduler.presentation.theme.PaddingMedium
import com.englesoft.appscheduler.presentation.theme.PaddingSmall
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    onNavigateBack: () -> Unit,
    viewModel: ScheduleViewModel = koinViewModel()
) {

    val screenState = viewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create app schedule",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            if (screenState.isLoading) {
                CircularProgressIndicator()
            }
            screenState.error?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
            if (screenState.apps.isNotEmpty()) {
                ScheduleScreenContent(
                    apps = screenState.apps,
                    onSchedule = { appInfo, triggerTime ->
                        viewModel.scheduleApp(appInfo, triggerTime)
                        onNavigateBack()
                    }
                )
            }
        }
    }
}

@Composable
private fun ScheduleScreenContent(
    apps: List<AppInfo>,
    onSchedule: (AppInfo, Long) -> Unit
) {

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedApp by remember { mutableStateOf<AppInfo?>(null) }

    if (showBottomSheet && selectedApp != null) {
        ScheduleBottomSheet(
            app = selectedApp!!,
            onSchedule = { triggerTime ->
                selectedApp?.let { app ->
                    onSchedule(app, triggerTime)
                    showBottomSheet = false
                }
            },
            onDismiss = { showBottomSheet = false }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PaddingMedium),
        verticalArrangement = Arrangement.spacedBy(PaddingExtraSmall)
    ) {
        items(apps) { app ->
            AppItem(
                app = app,
                onClick = {
                    selectedApp = app
                    showBottomSheet = true
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleBottomSheet(
    app: AppInfo,
    onSchedule: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val selectedCalendar = Calendar.getInstance()
    val selectedTimeValid = remember { mutableStateOf(true) }

    val datePickerState = rememberDatePickerState()

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
                text = "Schedule ${app.name} app",
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

@Composable
private fun AppItem(app: AppInfo, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CornerRadiusLarge))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .clickable { onClick() }
            .padding(PaddingMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(PaddingSmall)
    ) {
        Image(
            modifier = Modifier.size(45.dp),
            painter = rememberAsyncImagePainter(app.icon),
            contentDescription = app.name
        )
        Column {
            Text(
                text = app.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = app.packageName,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ScheduleScreenPreview() {
    val apps = listOf(
        AppInfo(
            name = "App 1",
            packageName = "com.example.app1",
            icon = R.drawable.ic_launcher_foreground.toDrawable()
        )
    )
    ScheduleScreenContent(
        apps = apps,
        onSchedule = { _, _ ->

        }
    )
}

