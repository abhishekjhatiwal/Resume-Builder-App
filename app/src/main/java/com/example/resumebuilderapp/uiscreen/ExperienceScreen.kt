package com.example.resumebuilderapp.uiscreen

import android.net.Uri
import androidx.compose.runtime.Composable
import com.example.resumebuilderapp.data.UiState

@Composable
fun ExperienceScreen(
    uiState: UiState,
    onPrev:() -> Unit,
    onNext:(Uri) -> Unit,
    onFailedChange: (company:String?, position: String?, startDate: String?, endDate: String?, description: String?) -> Unit,
){}