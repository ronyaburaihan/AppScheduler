package com.englesoft.appscheduler.presentation.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.englesoft.appscheduler.domain.model.ScheduleInfo
import com.englesoft.appscheduler.presentation.theme.CornerRadiusLarge
import com.englesoft.appscheduler.presentation.theme.PaddingExtraSmall
import com.englesoft.appscheduler.presentation.theme.PaddingMedium
import com.englesoft.appscheduler.presentation.theme.PaddingSmall
import com.englesoft.appscheduler.utils.convertToDateTime
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToSchedule: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {

    val screenState = viewModel.screenState

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "App Scheduler",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToSchedule
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add button"
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            screenState.error?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            HomeScreenContent(
                schedules = screenState.schedules,
                onReschedule = {

                }, onCancel = {

                }
            )
        }
    }
}

@Composable
private fun HomeScreenContent(
    schedules: List<ScheduleInfo>,
    onReschedule: () -> Unit,
    onCancel: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = PaddingMedium),
        verticalArrangement = Arrangement.spacedBy(PaddingExtraSmall),
    ) {
        items(schedules) { schedule ->
            ScheduleItem(
                schedule = schedule,
                onReschedule = onReschedule,
                onCancel = onCancel
            )
        }
    }
}

@Composable
private fun ScheduleItem(
    modifier: Modifier = Modifier,
    schedule: ScheduleInfo,
    onReschedule: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CornerRadiusLarge))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(PaddingMedium),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(PaddingSmall)
        ) {
            Image(
                modifier = Modifier.size(45.dp),
                painter = rememberAsyncImagePainter(schedule.icon),
                contentDescription = schedule.name
            )
            Column {
                Text(
                    text = schedule.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = schedule.packageName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Trigger time: ${schedule.triggerTime.convertToDateTime()}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onCancel
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.width(PaddingSmall))

            TextButton(
                onClick = onReschedule
            ) {
                Text(
                    text = "Reschedule",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }
    }
}