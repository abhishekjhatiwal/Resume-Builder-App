package com.example.resumebuilderapp.uiscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resumebuilderapp.data.UiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExperienceScreen(
    uiState: UiState,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onFailedChange: (
        company: String?,
        position: String?,
        startDate: String?,
        endDate: String?,
        description: String?
    ) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Resume", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(Color.Blue),
                navigationIcon = {TextButton(onClick = onPrev) { Text("Back", color = Color.White) }}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Experience", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.resume.experience.company,
                onValueChange = { onFailedChange(it, null, null, null, null) },
                label = { Text("Company Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
            OutlinedTextField(
                value = uiState.resume.experience.position,
                onValueChange = { onFailedChange(null, it, null, null, null) },
                label = { Text("Position") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = uiState.resume.experience.startDate,
                    onValueChange = { onFailedChange(null, null, it, null, null) },
                    label = { Text("Start Year") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                OutlinedTextField(
                    value = uiState.resume.experience.endDate,
                    onValueChange = { onFailedChange(null, null, null, it, null) },
                    label = { Text("End year") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                OutlinedTextField(
                    value = uiState.resume.experience.endDate,
                    onValueChange = { onFailedChange(null, null, null, null, it) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onNext, // Fixed: removed extra braces
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    enabled = uiState.resume.experience.company.isNotBlank() &&
                            uiState.resume.experience.position.isNotBlank()
                ) {
                    Text("Finish")
                }
            }
        }
    }
}


