package com.example.resumebuilderapp.data

import android.net.Uri

data class PersonalDetails(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val photoUrl: String = ""
)
data class QualificationSkills(
    val degree: String = "",
    val institute: String = "",
    val grade: String = "",
    val skills: String = "",
    val startDate: String = "",
    val endDate: String = ""
)

data class Experience(
    val company: String = "",
    val position: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val description: String = ""
)

data class ResumeData(
    val id : String = "",
    val personal: PersonalDetails = PersonalDetails(),
    val qualificationsSkills: QualificationSkills = QualificationSkills(),
    val experience: Experience = Experience()
)

data class UiState(
    val resume: ResumeData = ResumeData(),
    val locationPhotoUri: Uri? = null,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)
