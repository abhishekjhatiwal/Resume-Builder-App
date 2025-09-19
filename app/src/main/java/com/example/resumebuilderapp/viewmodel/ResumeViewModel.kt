package com.example.resumebuilderapp.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.resumebuilderapp.data.ResumeReposetiry
import com.example.resumebuilderapp.data.UiState
import com.example.resumebuilderapp.utils.toSoftwareBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResumeViewModel(app: Application) : AndroidViewModel(app) {
    private val resumeReposetiry = ResumeReposetiry()
    private var internalUiState = mutableStateOf(UiState())
    val uiState: UiState get() = internalUiState.value

    fun setPhotoUri(uri: Uri?) {
        internalUiState.value = uiState.copy(locationPhotoUri = uri)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun localBitmapOrNull(): Bitmap? {
        val appContext = getApplication<Application>()
        val uri = internalUiState.value.locationPhotoUri ?: return null
        return runCatching {
            val bitmap = if (Build.VERSION.SDK_INT >= 28) {
                val imgSource = ImageDecoder.createSource(appContext.contentResolver, uri)
                ImageDecoder.decodeBitmap(imgSource)
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(appContext.contentResolver, uri)
            }
            bitmap.toSoftwareBitmap()
        }.getOrNull()
    }

    fun updatePersonal(name: String?, email: String?, phone: String?, address: String?) {
        val currentResume = internalUiState.value.resume
        internalUiState.value = internalUiState.value.copy(
            resume = currentResume.copy(
                personal = currentResume.personal.copy(
                    name = name ?: currentResume.personal.name,
                    email = email ?: currentResume.personal.email,
                    phone = phone ?: currentResume.personal.phone,
                    address = address ?: currentResume.personal.address
                )
            )
        )
    }

    fun updateQulification(
        degree: String?,
        institute: String?,
        grade: String?,
        startDate: String?,
        endDate: String?,
        skills: String?
    ) {
        val currentResume = internalUiState.value.resume
        internalUiState.value = internalUiState.value.copy(
            resume = currentResume.copy(
                qualificationsSkills = currentResume.qualificationsSkills.copy(
                    degree = degree ?: currentResume.qualificationsSkills.degree,
                    institute = institute ?: currentResume.qualificationsSkills.institute,
                    grade = grade ?: currentResume.qualificationsSkills.grade,
                    startDate = startDate ?: currentResume.qualificationsSkills.startDate,
                    endDate = endDate ?: currentResume.qualificationsSkills.endDate,
                    skills = skills ?: currentResume.qualificationsSkills.skills
                )
            )
        )
    }

    fun updateExperience(
        company: String?,
        position: String?,
        startDate: String?,
        endDate: String?,
        description: String?
    ) {
        val currentResume = internalUiState.value.resume
        internalUiState.value = internalUiState.value.copy(
            resume = currentResume.copy(
                experience = currentResume.experience.copy(
                    company = company ?: currentResume.experience.company,
                    position = position ?: currentResume.experience.position,
                    startDate = startDate ?: currentResume.experience.startDate,
                    endDate = endDate ?: currentResume.experience.endDate,
                    description = description ?: currentResume.experience.description
                )
            )
        )
    }

    fun deleteAll(onDone: () -> Unit) {
        val id = internalUiState.value.resume.id
        viewModelScope.launch {
            internalUiState.value = internalUiState.value.copy(isSaving = true, errorMessage = null)
            runCatching {
                if (id.isEmpty()) resumeReposetiry.delete(id)
            }.onSuccess {
                internalUiState.value = UiState()
                onDone()
            }.onFailure {
                internalUiState.value =
                    internalUiState.value.copy(isSaving = false, errorMessage = it.message)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveToFirebase() {
        viewModelScope.launch {
            internalUiState.value = internalUiState.value.copy(isSaving = true, errorMessage = null)
            val bitmap = withContext(Dispatchers.IO) {
                localBitmapOrNull()
            }
            runCatching {
                resumeReposetiry.createOrUpdate(internalUiState.value.resume, bitmap)
            }.onSuccess { saved ->
                internalUiState.value = internalUiState.value.copy(
                    isSaving = false,
                    isSaved = true,
                    resume = saved
                )
            }.onFailure {
                internalUiState.value =
                    internalUiState.value.copy(isSaving = false, errorMessage = it.message)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun downloadPDF(uri: Uri?){
       // if(uri == null) return
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val bitmap = localBitmapOrNull()
                val appContext = getApplication<Application>()
                // generate pdf
            }.onSuccess {
                withContext(Dispatchers.Main){
                    internalUiState.value = internalUiState.value.copy(errorMessage = "PDF Generated Successfully")
                }
            }.onFailure {
                withContext(Dispatchers.Main){
                    internalUiState.value = internalUiState.value.copy(errorMessage = "PDF Saved failed ${it.message}")
                }
            }
        }
    }
}