package com.example.resumebuilderapp.uiscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
                    onValueChange = { onFailedChange(null, null, null, it, null) },
                    label = { Text("Start Year") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                OutlinedTextField(
                    value = uiState.resume.experience.endDate,
                    onValueChange = { onFailedChange(null, null, null, null, it) },
                    label = { Text("End year") },
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
                    onClick = { onNext },
                    colors = ButtonDefaults.buttonColors(Color.Blue),
                //    enabled = uiState.resume.qualificationsSkills.degree.isNotBlank() && uiState.resume.qualificationsSkills.institute.isNotBlank()
                ){
                    Text("Finish")
                }
            }
        }
    }
}


















































/*
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
    // Local state for fields
    var company by remember { mutableStateOf(TextFieldValue(uiState.resume.experience.company)) }
    var position by remember { mutableStateOf(TextFieldValue(uiState.resume.experience.position)) }
    var startDate by remember { mutableStateOf(TextFieldValue(uiState.resume.experience.startDate)) }
    var endDate by remember { mutableStateOf(TextFieldValue(uiState.resume.experience.endDate)) }
    var description by remember { mutableStateOf(TextFieldValue(uiState.resume.experience.description)) }

    // Sync with ViewModel
    LaunchedEffect(company.text, position.text, startDate.text, endDate.text, description.text) {
        onFailedChange(
            company.text.ifBlank { null },
            position.text.ifBlank { null },
            startDate.text.ifBlank { null },
            endDate.text.ifBlank { null },
            description.text.ifBlank { null }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Work Experience", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Company
        OutlinedTextField(
            value = company,
            onValueChange = { company = it },
            label = { Text("Company Name*") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Position
        OutlinedTextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("Job Title/Position*") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Date Row
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text("Start Date") },
                placeholder = { Text("MM/YYYY") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "Start Date")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text("End Date") },
                placeholder = { Text("MM/YYYY") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "End Date")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Description
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Job Description") },
            placeholder = { Text("Describe your responsibilities & achievements") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Navigation Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = onPrev, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Previous")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    if (isExperienceValid(company.text, position.text)) {
                        onNext()
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = isExperienceValid(company.text, position.text)
            ) {
                Text("Next")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null)
            }
        }
    }
}

// Validation
private fun isExperienceValid(company: String, position: String): Boolean {
    return company.isNotBlank() && position.isNotBlank()
}


 */