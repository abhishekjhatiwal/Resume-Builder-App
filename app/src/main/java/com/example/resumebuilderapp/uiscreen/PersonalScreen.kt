package com.example.resumebuilderapp.uiscreen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.resumebuilderapp.data.UiState

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
