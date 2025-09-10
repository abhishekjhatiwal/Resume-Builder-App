package com.example.resumebuilderapp.uiscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
        degree: String,
        institute: String,
        grade: String,
        startDate: String,
        endDate: String,
        skills: String
    ) -> Unit
) {
    // Extract current values from UiState
    var degree by remember { mutableStateOf(uiState.resume.qualificationsSkills.degree) }
    var institute by remember { mutableStateOf(uiState.resume.qualificationsSkills.institute) }
    var grade by remember { mutableStateOf(uiState.resume.qualificationsSkills.grade) }
    var startDate by remember { mutableStateOf(uiState.resume.qualificationsSkills.startDate) }
    var endDate by remember { mutableStateOf(uiState.resume.qualificationsSkills.endDate) }
    var skills by remember { mutableStateOf(uiState.resume.qualificationsSkills.skills) }

    // Whenever any field changes, update ViewModel
    LaunchedEffect(degree, institute, grade, startDate, endDate, skills) {
        onFieldsChange(degree, institute, grade, startDate, endDate, skills)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Text(
            text = "Educational Qualifications",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Education Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Education Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Degree Field
                OutlinedTextField(
                    value = degree,
                    onValueChange = { degree = it },
                    label = { Text("Degree/Qualification*") },
                    placeholder = { Text("e.g., B.Tech, MBA") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    singleLine = true
                )

                // Institute Field
                OutlinedTextField(
                    value = institute,
                    onValueChange = { institute = it },
                    label = { Text("Institute/University*") },
                    placeholder = { Text("e.g., ABC University") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    singleLine = true
                )

                // Grade Field
                OutlinedTextField(
                    value = grade,
                    onValueChange = { grade = it },
                    label = { Text("Grade/CGPA") },
                    placeholder = { Text("e.g., 8.5 CGPA, 85%") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    singleLine = true
                )

                // Date Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
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
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Skills Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Skills & Technologies",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = skills,
                    onValueChange = { skills = it },
                    label = { Text("Skills") },
                    placeholder = { Text("e.g., Java, Kotlin, Android, React") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 5
                )

                Text(
                    text = "Separate multiple skills with commas",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Navigation Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = onPrev, modifier = Modifier.weight(1f)) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Previous")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { onNext() },
                modifier = Modifier.weight(1f),
                enabled = isFormValid(degree, institute)
            ) {
                Text("Next")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Text(
            text = "* Required fields",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

// Validation function
private fun isFormValid(degree: String, institute: String): Boolean {
    return degree.isNotBlank() && institute.isNotBlank()
}


//@Composable
//fun QulificationScreen(
//    uiState: UiState,
//    onPrev:() -> Unit,
//    onNext:(Uri) -> Unit,
//    onFailedChange: (degree:String?, institute: String?, grade: String?, startDate: String?, endDate: String?, skills: String?) -> Unit,
//){}