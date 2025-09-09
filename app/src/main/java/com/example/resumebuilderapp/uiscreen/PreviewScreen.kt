package com.example.resumebuilderapp.uiscreen

import androidx.compose.runtime.Composable
import com.example.resumebuilderapp.data.UiState

@Composable
fun PreviewScreen(
    uiState: UiState,
    onEdit:() -> Unit,
    onDelete:() -> Unit,
    onSaveDatabase:() -> Unit,
    onDownloadPDF:() -> Unit,
){}