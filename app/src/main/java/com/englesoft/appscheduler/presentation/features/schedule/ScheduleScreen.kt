package com.englesoft.appscheduler.presentation.features.schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.englesoft.appscheduler.presentation.theme.PaddingMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    onNavigateBack: () -> Unit,
) {
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
            modifier = Modifier.padding(it)
        ) {
            ScheduleScreenContent()
        }
    }
}

@Composable
private fun ScheduleScreenContent() {
    Column(
        modifier = Modifier.padding(horizontal = PaddingMedium)
    ) {
        Text(
            "Schedule Screen",
            style = MaterialTheme.typography.titleLarge
        )
    }
}