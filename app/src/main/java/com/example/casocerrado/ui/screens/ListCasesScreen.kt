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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.casocerrado.ui.components.SearchBar
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

            Button(
                onClick = onNavigateToCreate,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("+ New case")
            }
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
                CaseListItem(
                    case = case,
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
        // la rayita solo se dibuja si esta seleccionada, si no dejo un espacio vacio
        // del mismo alto pa que las demas pestañas no salten de posicion
        Box(
            modifier = Modifier
                .height(2.dp)
                .width(24.dp)
                .background(if (selected) Color(0xFF1B2A4A) else Color.Transparent)
        )
    }
}

@Composable
fun CaseListItem(case: Case, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(Color(0xFF3A3A3C), RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = case.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Case #${case.id}", fontSize = 12.sp, color = Color.Gray)
                Text(text = case.fecha, fontSize = 12.sp, color = Color.Gray)
            }

            Column(horizontalAlignment = Alignment.End) {
                val badgeColor = if (case.state == EstateCase.CLOSED) {
                    Color(0xFFB2F2BB)
                } else {
                    Color(0xFFFFE066)
                }

                Box(
                    modifier = Modifier
                        .background(badgeColor, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(text = case.state.label, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = ">", color = Color.Gray, fontWeight = FontWeight.Bold)
            }
        }
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