package com.example.casocerrado.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.casocerrado.ui.screens.HomeScreen
import com.example.casocerrado.ui.screens.ListCasesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable(route = "home") {
            HomeScreen(
                onNavigateToList = { navController.navigate(route = "list") },
                onNavigateToCreate = { navController.navigate(route = "create") },
                onNavigateToStats = { /* no statistics screen yet */ },
                onNavigateToClosedCases = { navController.navigate(route = "list") }
            )
        }
        composable(route = "list") {
            ListCasesScreen(
                onCaseClick = { case ->
                    navController.navigate(route = "detail/${case.id}")
                },
                onNavigateToCreate = {
                    navController.navigate(route = "create")
                }
            )
        }

        // estas siguen con el texto de relleno pq esas pantallas todavia no existen
        composable(route = "create") { Text("Create case") }

        composable(
            route = "detail/{caseId}",
            arguments = listOf(navArgument(name = "caseId") { type = NavType.IntType })
        ) { Text("Case detail") }

        composable(
            route = "edit/{caseId}",
            arguments = listOf(navArgument(name = "caseId") { type = NavType.IntType })
        ) { Text("Edit case") }
    }
}