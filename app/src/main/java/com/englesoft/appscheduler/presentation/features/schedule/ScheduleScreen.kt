package com.englesoft.appscheduler.presentation.features.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toDrawable
import coil.compose.rememberAsyncImagePainter
import com.englesoft.appscheduler.R
import com.englesoft.appscheduler.domain.model.AppInfo
import com.englesoft.appscheduler.presentation.features.components.DateTimePickerSheet
import com.englesoft.appscheduler.presentation.theme.CornerRadiusLarge
import com.englesoft.appscheduler.presentation.theme.PaddingMedium
import com.englesoft.appscheduler.presentation.theme.PaddingSmall
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    onNavigateBack: () -> Unit,
    viewModel: ScheduleViewModel = koinViewModel()
) {

    val screenState = viewModel.state.value

    LaunchedEffect(screenState.isScheduleSuccess) {
        if (screenState.isScheduleSuccess) {
            onNavigateBack()
        }
    }

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
            ScheduleScreenContent(
                apps = screenState.apps,
                onSchedule = { appInfo, triggerTime ->
                    viewModel.scheduleApp(appInfo, triggerTime)
                },
                errorMessage = screenState.error,
                onErrorDismiss = {
                    viewModel.clearErrorMessages()
                }
            )
        }
    }
}

@Composable
private fun ScheduleScreenContent(
    apps: List<AppInfo>,
    errorMessage: String? = null,
    onSchedule: (AppInfo, Long) -> Unit,
    onErrorDismiss: () -> Unit
) {

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedApp by remember { mutableStateOf<AppInfo?>(null) }

    if (showBottomSheet && selectedApp != null) {
        DateTimePickerSheet(
            appName = selectedApp!!.name,
            onSchedule = { triggerTime ->
                selectedApp?.let { app ->
                    onSchedule(app, triggerTime)
                    showBottomSheet = false
                }
            },
            onDismiss = { showBottomSheet = false }
        )
    }

    errorMessage?.let {
        ErrorDialog(
            errorMessage = errorMessage,
            onDismiss = onErrorDismiss
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PaddingMedium),
        verticalArrangement = Arrangement.spacedBy(PaddingSmall)
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

@Composable
fun ErrorDialog(errorMessage: String, onDismiss: () -> Unit) {
    AlertDialog(
        title = {
            Text(text = "Error")
        },
        text = {
            Text(text = errorMessage)
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
            ) {
                Text("Got it")
            }
        },
    )
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

        },
        onErrorDismiss = {

        }
    )
}

