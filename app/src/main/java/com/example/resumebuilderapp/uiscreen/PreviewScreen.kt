package com.example.resumebuilderapp.uiscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.resumebuilderapp.data.UiState
import android.R
import android.R.id.title
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerDefaults.colors
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.ActivityNavigatorExtras
import coil.compose.AsyncImage
import org.jetbrains.annotations.Async


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(
    uiState: UiState,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onSaveDatabase: () -> Unit,
    onDownloadPDF: (Uri?) -> Unit,
) {

    val createPDF =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application_/pdf")) { uri ->
            onDownloadPDF(uri)
        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Resume Preview") },
                colors = TopAppBarDefaults.topAppBarColors(Color.White),
                navigationIcon = {
                    TextButton(onClick = onEdit) {
                        Text(
                            "Edit",
                            color = Color.Black
                        )
                    }
                },
                actions = {
                    TextButton(onClick = onDelete) { Text("Delete", color = Color.Black) }
                    //  TextButton(onClick = { onSaveDatabase() }) { Text("Save", color = Color.Black) }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Personal Info
            Text("Resume Preview", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))

            // Resume Card
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { onSaveDatabase() },
                    enabled = !uiState.isSaving,
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Text( if (uiState.isSaving) "Saving..." else "Save",
                        color = Color.White
                        )
                }
                Button(onClick = { createPDF.launch("resume_${System.currentTimeMillis()}.pdf") },
                 //   enabled = !uiState.isSaving,
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Text("Download PDF", color = Color.White)
                }
            }
            uiState.errorMessage?.let{
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}









































/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(
    uiState: UiState,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onSaveDatabase: () -> Unit,
    onDownloadPDF: () -> Unit,
) {
    val resume = uiState.resume

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Resume Preview") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Personal Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Personal Details", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(8.dp))

                    resume.personal.photoUrl.takeIf { it.isNotEmpty() }?.let { photo ->
                        Image(
                            painter = rememberAsyncImagePainter(photo),
                            contentDescription = "Profile Photo",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.CenterHorizontally),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Text("Name: ${resume.personal.name}")
                    Text("Email: ${resume.personal.email}")
                    Text("Phone: ${resume.personal.phone}")
                    Text("Address: ${resume.personal.address}")
                }
            }

            // Qualification Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Education", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Degree: ${resume.qualificationsSkills.degree}")
                    Text("Institute: ${resume.qualificationsSkills.institute}")
                    Text("Grade: ${resume.qualificationsSkills.grade}")
                    Text("Duration: ${resume.qualificationsSkills.startDate} - ${resume.qualificationsSkills.endDate}")
                    Text("Skills: ${resume.qualificationsSkills.skills}")
                }
            }

            // Experience Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Experience", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Company: ${resume.experience.company}")
                    Text("Position: ${resume.experience.position}")
                    Text("Duration: ${resume.experience.startDate} - ${resume.experience.endDate}")
                    Text("Description: ${resume.experience.description}")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Edit")
                }

                OutlinedButton(onClick = onDelete, colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = onSaveDatabase) {
                    Icon(Icons.Default.AddPhotoAlternate, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save")
                }

                Button(onClick = onDownloadPDF) {
                    Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Download PDF")
                }
            }
        }
    }
}


 */