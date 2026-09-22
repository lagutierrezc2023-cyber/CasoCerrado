package com.example.casocerrado.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.casocerrado.data.model.Case




@Composable
fun SearchBar(
    searchText: String,
    onSearchChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = { newText -> onSearchChange(newText) },

        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = { Text("Search case by title...") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp)
    )
}


fun filterCases(cases: List<Case>, query: String): List<Case> {
    if (query.isBlank()) {
        return cases
    }

    return cases.filter { case -> case.title.contains(query, ignoreCase = true) }
}