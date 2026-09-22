package com.example.casocerrado.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.casocerrado.data.model.Case
import com.example.casocerrado.data.model.EstateCase
import com.example.casocerrado.ui.components.CasoCard
import com.example.casocerrado.ui.components.SearchBar
import com.example.casocerrado.ui.components.SmallActionButton
import com.example.casocerrado.ui.components.filterCases


@Composable
fun ListCasesScreen(
    cases: List<Case> = mockCases(),
    onCaseClick: (Case) -> Unit,
    onNavigateToCreate: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf("all") }

    val bySearch = filterCases(cases, searchText)

    val filteredCases = when (selectedTab) {
        "investigation" -> bySearch.filter { it.state == EstateCase.IN_INVESTIGATION }
        "closed" -> bySearch.filter { it.state == EstateCase.CLOSED }
        else -> bySearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F7))
            .padding(16.dp)
            .padding(top = 30.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My cases",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            SmallActionButton(
                text = "+ New case",
                onClick = onNavigateToCreate
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SearchBar(
            searchText = searchText,
            onSearchChange = { searchText = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TabText(
                text = "All (${cases.size})",
                selected = selectedTab == "all",
                onClick = { selectedTab = "all" }
            )
            TabText(
                text = "In investigation (${cases.count { it.state == EstateCase.IN_INVESTIGATION }})",
                selected = selectedTab == "investigation",
                onClick = { selectedTab = "investigation" }
            )
            TabText(
                text = "Closed (${cases.count { it.state == EstateCase.CLOSED }})",
                selected = selectedTab == "closed",
                onClick = { selectedTab = "closed" }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredCases) { case ->
                CasoCard(
                    caso = case,
                    onClick = { onCaseClick(case) }
                )
            }
        }
    }
}

@Composable
fun TabText(text: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            color = if (selected) Color(0xFF1B2A4A) else Color.Gray,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .height(2.dp)
                .width(24.dp)
                .background(if (selected) Color(0xFF1B2A4A) else Color.Transparent)
        )
    }
}



fun mockCases(): List<Case> {
    return listOf(
        Case(
            id = 1,
            title = "Robbery at the gallery",
            description = "Several pieces stolen from the central gallery",
            fecha = "12 Mar 2025",
            state = EstateCase.IN_INVESTIGATION
        ),
        Case(
            id = 2,
            title = "Corporate fraud",
            description = "Money missing from company accounts",
            fecha = "5 Mar 2025",
            state = EstateCase.IN_INVESTIGATION
        ),
        Case(
            id = 3,
            title = "Missing person",
            description = "Person disappeared near downtown",
            fecha = "20 Feb 2025",
            state = EstateCase.CLOSED
        )
    )
}