package com.example.resumebuilderapp.uiscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resumebuilderapp.data.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QualificationScreen(
    uiState: UiState,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onFieldsChange: (
        degree: String?,
        institute: String?,
        grade: String?,
        startDate: String?,
        endDate: String?,
        skills: String?
    ) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Resume", color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Blue
                ),
                navigationIcon = {
                    TextButton(onClick = onPrev) {
                        Text("Back", color = Color.White)
                    }
                }
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
            Text("Educational Qualifications", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            // Degree field
            OutlinedTextField(
                value = uiState.resume.qualificationsSkills.degree,
                onValueChange = { onFieldsChange(it, null, null, null, null, null) },
                label = { Text("Degree") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            // Institute field - Fixed value binding
            OutlinedTextField(
                value = uiState.resume.qualificationsSkills.institute,
                onValueChange = { onFieldsChange(null, it, null, null, null, null) },
                label = { Text("Institute") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            // Grade field - Fixed value binding
            OutlinedTextField(
                value = uiState.resume.qualificationsSkills.grade,
                onValueChange = { onFieldsChange(null, null, it, null, null, null) },
                label = { Text("Grade") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            // Date fields - Fixed value binding and layout
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = uiState.resume.qualificationsSkills.startDate,
                    onValueChange = { onFieldsChange(null, null, null, it, null, null) },
                    label = { Text("Start Year") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = uiState.resume.qualificationsSkills.endDate,
                    onValueChange = { onFieldsChange(null, null, null, null, it, null) },
                    label = { Text("End Year") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            // Skills field - Fixed value binding
            OutlinedTextField(
                value = uiState.resume.qualificationsSkills.skills,
                onValueChange = { onFieldsChange(null, null, null, null, null, it) },
                label = { Text("Skills") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                minLines = 3,
                maxLines = 5
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onNext() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
//                    enabled = uiState.resume.qualificationsSkills.degree.isNotBlank() &&
//                            uiState.resume.qualificationsSkills.institute.isNotBlank()
                ) {
                    Text("Next")
                }
            }
        }
    }
}
















































































/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QualificationScreen(
    uiState: UiState,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onFieldsChange: (
        degree: String?,
        institute: String?,
        grade: String?,
        startDate: String?,
        endDate: String?,
        skills: String?
    ) -> Unit
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
            Text("Educational Qualifications", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.resume.qualificationsSkills.degree,
                onValueChange = { onFieldsChange(it, null, null, null, null, null) },
                label = { Text("Degree") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
            OutlinedTextField(
                value = uiState.resume.qualificationsSkills.degree,
                onValueChange = { onFieldsChange(null, it, null, null, null, null) },
                label = { Text("Institute") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
            OutlinedTextField(
                value = uiState.resume.qualificationsSkills.degree,
                onValueChange = { onFieldsChange(null, null, it, null, null, null) },
                label = { Text("Grade") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = uiState.resume.qualificationsSkills.degree,
                    onValueChange = { onFieldsChange(null, null, null, it, null, null) },
                    label = { Text("Start Year") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                OutlinedTextField(
                    value = uiState.resume.qualificationsSkills.degree,
                    onValueChange = { onFieldsChange(null, null, null, null, it, null) },
                    label = { Text("End year") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
            }
            OutlinedTextField(
                value = uiState.resume.qualificationsSkills.degree,
                onValueChange = { onFieldsChange(null, null, null, null, null, it) },
                label = { Text("Skills") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onNext() },
                    colors = ButtonDefaults.buttonColors(Color.Blue),
                    enabled = uiState.resume.qualificationsSkills.degree.isNotBlank() && uiState.resume.qualificationsSkills.institute.isNotBlank()
                ){
                    Text("Next")
                }
            }
        }
    }
}


 */