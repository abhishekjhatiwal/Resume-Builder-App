package com.example.resumebuilderapp.utils

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
fun Bitmap.toSoftwareBitmap(): Bitmap {
    return if (this.config == Bitmap.Config.HARDWARE) {
        this.copy(Bitmap.Config.ARGB_8888, true)
    }else{
        this
    }
}