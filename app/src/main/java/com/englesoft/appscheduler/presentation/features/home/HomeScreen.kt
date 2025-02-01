package com.englesoft.appscheduler.presentation.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.englesoft.appscheduler.presentation.theme.PaddingMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToSchedule: () -> Unit
) {
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
            HomeScreenContent()
        }
    }
}

@Composable
private fun HomeScreenContent() {
    Column(
        modifier = Modifier.padding(horizontal = PaddingMedium)
    ) {
        Text(
            "Home Screen",
            style = MaterialTheme.typography.titleLarge
        )
    }
}