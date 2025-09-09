package com.example.resumebuilderapp.uiscreen

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.resumebuilderapp.data.UiState
import com.example.resumebuilderapp.viewmodel.ResumeViewModel

@Composable
fun QulificationScreen(
    uiState: UiState,
    onPrev:() -> Unit,
    onNext:(Uri) -> Unit,
    onFailedChange: (degree:String?, institute: String?, grade: String?, startDate: String?, endDate: String?, skills: String?) -> Unit,
){}