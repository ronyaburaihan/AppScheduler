package com.englesoft.appscheduler.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.englesoft.appscheduler.presentation.features.home.HomeScreen
import com.englesoft.appscheduler.presentation.features.schedule.ScheduleScreen

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = HomeScreen) {
        composable<HomeScreen> {
            HomeScreen(
                onNavigateToSchedule = { navController.navigate(ScheduleScreen) }
            )
        }
        composable<ScheduleScreen> {
            ScheduleScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}