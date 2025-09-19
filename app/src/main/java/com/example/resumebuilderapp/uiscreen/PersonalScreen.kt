package com.example.resumebuilderapp.uiscreen

import android.R
import android.R.id.title
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.material3.DatePickerDefaults.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.resumebuilderapp.data.UiState
import androidx.compose.material.icons.filled.Person


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalScreen(
    uiState: UiState,
    onNext: () -> Unit,
    onPhotoPicked: (uri: Uri?) -> Unit,
    onFailedChange: (name: String?, email: String?, phone: String?, address: String?) -> Unit,
) {
    val pickPhoto =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            onPhotoPicked(uri)
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personal Details", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Personal Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // Photo section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.locationPhotoUri != null) {
                    AsyncImage(
                        model = uiState.locationPhotoUri,
                        contentDescription = "Selected Photo",
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Placeholder when no photo is selected
                    Card(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape),
                        colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.3f))
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "No photo selected",
                                modifier = Modifier.size(48.dp),
                                tint = Color.Gray
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        pickPhoto.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text(if (uiState.resume.personal.photoUrl.isEmpty()) "Add Photo" else "Change Photo")
                }
            }

            // Input fields
            OutlinedTextField(
                value = uiState.resume.personal.name,
                onValueChange = { onFailedChange(it, null, null, null) },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = uiState.resume.personal.email,
                onValueChange = { onFailedChange(null, it, null, null) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            OutlinedTextField(
                value = uiState.resume.personal.phone,
                onValueChange = { onFailedChange(null, null, it, null) },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            OutlinedTextField(
                value = uiState.resume.personal.address,
                onValueChange = { onFailedChange(null, null, null, it) },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Next button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onNext,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    enabled = uiState.resume.personal.name.isNotBlank() &&
                            uiState.resume.personal.email.isNotBlank() &&
                            uiState.resume.personal.phone.isNotBlank() &&
                            uiState.resume.personal.address.isNotBlank()
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
fun PersonalScreen(
    uiState: UiState,
    onNext: () -> Unit,
    onPhotoPicked: (Uri) -> Unit,
    onFailedChange: (name: String?, email: String?, phone: String?, address: String?) -> Unit,
) {
    // Local state for text fields
    var name by remember { mutableStateOf(TextFieldValue(uiState.resume.personal.name)) }
    var email by remember { mutableStateOf(TextFieldValue(uiState.resume.personal.email)) }
    var phone by remember { mutableStateOf(TextFieldValue(uiState.resume.personal.phone)) }
    var address by remember { mutableStateOf(TextFieldValue(uiState.resume.personal.address)) }

    // Sync with ViewModel whenever input changes
    LaunchedEffect(name.text, email.text, phone.text, address.text) {
        onFailedChange(
            name.text.ifBlank { null },
            email.text.ifBlank { null },
            phone.text.ifBlank { null },
            address.text.ifBlank { null }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Personal Information", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Photo Section
        Box(contentAlignment = Alignment.Center) {
            if (uiState.resume.personal.photoUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(uiState.resume.personal.photoUrl),
                    contentDescription = "Profile Photo",
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Upload Photo",
                    modifier = Modifier.size(100.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Name field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name*") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email*") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Phone field
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone*") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Address field
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address*") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Next Button
        Button(
            onClick = {
                if (isFormValid(name.text, email.text, phone.text, address.text)) {
                    onNext()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid(name.text, email.text, phone.text, address.text)
        ) {
            Text("Next")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
    }
}

// Validation function
private fun isFormValid(name: String, email: String, phone: String, address: String): Boolean {
    return name.isNotBlank() && email.isNotBlank() && phone.isNotBlank() && address.isNotBlank()
}


 */