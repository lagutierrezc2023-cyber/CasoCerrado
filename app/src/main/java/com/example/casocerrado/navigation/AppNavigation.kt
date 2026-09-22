package com.example.casocerrado.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.casocerrado.data.model.Case
import com.example.casocerrado.data.repository.CasesManaging
import com.example.casocerrado.domain.CasesManagement
import com.example.casocerrado.ui.screens.CreateCaseScreen
import com.example.casocerrado.ui.screens.DetailCaseScreen
import com.example.casocerrado.ui.screens.EditCaseScreen
import com.example.casocerrado.ui.screens.HomeScreen
import com.example.casocerrado.ui.screens.ListCasesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val casesManaging = remember { CasesManaging() }
    val casesManagement = remember { CasesManagement(casesManaging) }
    var casesList by remember { mutableStateOf<List<Case>>(casesManagement.getCases()) }

    fun refreshCases() {
        casesList = casesManagement.getCases()
    }

    NavHost(navController = navController, startDestination = "home") {

        composable(route = "home") {
            HomeScreen(
                onNavigateToList = { navController.navigate(route = "list") },
                onNavigateToCreate = { navController.navigate(route = "create") },
                onNavigateToStats = { navController.navigate(route = "list") },
                onNavigateToClosedCases = { navController.navigate(route = "list") }
            )
        }

        composable(route = "list") {
            refreshCases()

            ListCasesScreen(
                cases = casesList,
                onCaseClick = { case ->
                    navController.navigate(route = "detail/${case.id}")
                },
                onNavigateToCreate = {
                    navController.navigate(route = "create")
                }
            )
        }

        composable(route = "create") {
            CreateCaseScreen(
                onSave = { title, description, date ->
                    val result = casesManagement.createCase(title, description, date)
                    if (result == "") {
                        refreshCases()
                    }
                    result
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // aca ya conecto DetailCaseScreen de verdad
        composable(
            route = "detail/{caseId}",
            arguments = listOf(navArgument(name = "caseId") { type = NavType.IntType })
        ) { backStackEntry ->

            // saco el id de la ruta y busco el caso completo en la lista cargada
            val caseId = backStackEntry.arguments?.getInt("caseId") ?: 0
            val selectedCase = casesList.find { it.id == caseId }

            if (selectedCase == null) {
                // no deberia pasar nunca en uso normal, pero evita un crash
                Text("Case not found")
            } else {
                DetailCaseScreen(
                    case = selectedCase,
                    onBack = {
                        navController.popBackStack()
                    },
                    onEdit = {
                        navController.navigate(route = "edit/${selectedCase.id}")
                    }
                )
            }
        }

        composable(
            route = "edit/{caseId}",
            arguments = listOf(navArgument(name = "caseId") { type = NavType.IntType })
        ) { backStackEntry ->

            val caseId = backStackEntry.arguments?.getInt("caseId") ?: 0
            val selectedCase = casesList.find { it.id == caseId }

            if (selectedCase == null) {
                Text("Case not found")
            } else {
                EditCaseScreen(
                    case = selectedCase,
                    onSave = { id, title, description, date ->
                        val result = casesManagement.editCase(id, title, description, date)
                        if (result == "") {
                            refreshCases()
                        }
                        result
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}