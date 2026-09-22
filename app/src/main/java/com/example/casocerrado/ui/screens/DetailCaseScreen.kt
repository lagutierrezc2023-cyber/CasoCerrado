package com.example.casocerrado.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
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
import com.example.casocerrado.data.model.Evidence
import com.example.casocerrado.data.model.TypeEvidence
import com.example.casocerrado.ui.components.DeleteButton

@Composable
fun DetailCaseScreen(
    case: Case,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onCloseCase: () -> String,
    onReopenCase: () -> String,
    onDeleteCase: () -> String,
    onAddEvidence: (type: TypeEvidence, description: String) -> String
) {

    var errorMessage by remember { mutableStateOf("") }

    var selectedType by remember { mutableStateOf(TypeEvidence.PHOTO) }
    var evidenceDescription by remember { mutableStateOf("") }
    var evidenceMessage by remember { mutableStateOf("") }
    var evidenceMessageIsError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F7))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(top = 30.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = case.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Case #${case.id}",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            var badgeColor = Color(0xFFFFE066)
            if (case.state == EstateCase.CLOSED) {
                badgeColor = Color(0xFFB2F2BB)
            }

            Row(
                modifier = Modifier
                    .background(badgeColor, RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(text = case.state.label, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(text = case.fecha, color = Color.Gray, fontSize = 13.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Description", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = case.description, color = Color.DarkGray)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Investigation summary", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Findings recorded: ${case.finding.size}")
                Text(text = "Evidence items recorded: ${case.evidence.size}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Add evidence", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (tipo in TypeEvidence.entries) {
                        val estaSeleccionado = tipo == selectedType

                        Text(
                            text = tipo.label,
                            fontSize = 12.sp,
                            color = if (estaSeleccionado) Color.White else Color.DarkGray,
                            modifier = Modifier
                                .background(
                                    if (estaSeleccionado) Color(0xFF1B2A4A) else Color(0xFFE0E0E0),
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable { selectedType = tipo }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = evidenceDescription,
                    onValueChange = { evidenceDescription = it },
                    label = { Text("Evidence description") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                if (evidenceMessage != "") {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = evidenceMessage,
                        color = if (evidenceMessageIsError) Color.Red else Color(0xFF2E7D32),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        val result = onAddEvidence(selectedType, evidenceDescription)

                        if (result == "") {
                            evidenceDescription = ""
                            evidenceMessage = "Evidence added successfully"
                            evidenceMessageIsError = false
                        } else {
                            evidenceMessage = result
                            evidenceMessageIsError = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Add evidence")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Recorded evidence", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                if (case.evidence.isEmpty()) {
                    Text(text = "No evidence added yet", color = Color.Gray, fontSize = 13.sp)
                } else {
                    for (item in case.evidence) {
                        EvidenceRow(evidence = item)
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (errorMessage != "") {
            Text(
                text = errorMessage,
                color = Color.Red
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = onEdit,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Edit case")
            }


            if (case.state == EstateCase.CLOSED) {
                Button(
                    onClick = {
                        val result = onReopenCase()
                        if (result != "") {
                            errorMessage = result
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Reopen case")
                }
            } else {
                Button(
                    onClick = {
                        val result = onCloseCase()
                        if (result != "") {
                            errorMessage = result
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD64545))
                ) {
                    Text("Close case")
                }
            }
            DeleteButton(
                text = "Delete case",
                onClick = {
                    val result = onDeleteCase()
                    if (result != "") {
                        errorMessage = result
                    } else {
                        onBack()
                    }
                }
            )

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Back")
            }
        }
    }
}

@Composable
fun EvidenceRow(evidence: Evidence) {
    Text(
        text = "${evidence.type.label}: ${evidence.description}",
        fontSize = 13.sp,
        color = Color.DarkGray
    )
}