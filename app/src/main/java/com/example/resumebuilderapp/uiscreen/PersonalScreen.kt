package com.example.resumebuilderapp.uiscreen

import android.net.Uri
import com.example.resumebuilderapp.data.UiState

fun PersonalScreen(
    uiState: UiState,
    onNext:() -> Unit,
    onPhotoPicked:(Uri) -> Unit,
    onFailedChange: (name:String?, email: String?, phone: String?, address: String?) -> Unit,
) {
    //
}