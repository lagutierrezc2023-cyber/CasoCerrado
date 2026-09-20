package com.example.casocerrado.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.casocerrado.ui.screens.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(
                onNavigateToList = { navController.navigate("list") },
                onNavigateToCreate = { navController.navigate("create") },
                onNavigateToStats = { /* no statistics screen yet */ },
                onNavigateToClosedCases = { navController.navigate("list") }
            )
        }
        composable("list") { Text("Case list") }

        composable("create") { Text("Create case") }

        composable(
            route = "detail/{caseId}",
            arguments = listOf(navArgument("caseId") { type = NavType.IntType })
        ) { Text("Case detail") }

        composable(
            route = "edit/{caseId}",
            arguments = listOf(navArgument("caseId") { type = NavType.IntType })
        ) { Text("Edit case") }
    }
}