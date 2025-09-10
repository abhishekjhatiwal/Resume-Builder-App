package com.example.resumebuilderapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.resumebuilderapp.uiscreen.ExperienceScreen
import com.example.resumebuilderapp.uiscreen.PersonalScreen
import com.example.resumebuilderapp.uiscreen.PreviewScreen
import com.example.resumebuilderapp.uiscreen.QualificationScreen
import com.example.resumebuilderapp.viewmodel.ResumeViewModel

object Routes {
    const val PERSONAL = "personal"
    const val QUALIFICATION = "qualifications"
    const val EXPERIENCE = "experience"
    const val PREVIEW = "preview"
//    const val SAVE = "save"
//    const val DELETE = "delete"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    val resumeViewModel: ResumeViewModel = viewModel()
    NavHost(navController = navController, startDestination = Routes.PERSONAL) {
        composable(Routes.PERSONAL) {
            PersonalScreen(
                uiState = resumeViewModel.uiState,
                onNext = {
                    navController.navigate(Routes.QUALIFICATION)
                },
                onPhotoPicked = {
                    resumeViewModel.setPhotoUri(it)
                },
                onFailedChange = { name, email, phone, address ->
                    resumeViewModel.updatePersonal(name, email, phone, address)
                }
            )
        }
        composable(Routes.QUALIFICATION) {
//            QualificationScreen(
//                uiState = resumeViewModel.uiState,
//                onPrev = {
//                    navController.popBackStack()
//                },
//                onNext = {
//                    navController.navigate(Routes.EXPERIENCE)
//                },
//                onFailedChange = { degree, institute, grade, startDate, endDate, skills ->
////                    resumeViewModel.updateQulification(degree, institute, grade, startDate, endDate, skills)
//                } //  resumeViewModel::updateQulification
//            )
            QualificationScreen(
                uiState = resumeViewModel.uiState,
                onPrev = { navController.popBackStack() },
                onNext = { navController.navigate(Routes.EXPERIENCE) },
                onFieldsChange = { degree, institute, grade, startDate, endDate, skills ->
                    resumeViewModel.updateQulification(
                        degree,
                        institute,
                        grade,
                        startDate,
                        endDate,
                        skills
                    )
                }
            )

        }
        composable(Routes.EXPERIENCE) {
            ExperienceScreen(
                uiState = resumeViewModel.uiState,
                onPrev = {
                    navController.popBackStack()
                },
                onNext = {
                    navController.navigate(Routes.PREVIEW)
                },
                onFailedChange = { company, position, startDate, endDate, description ->
                    resumeViewModel.updateExperience(
                        company,
                        position,
                        startDate,
                        endDate,
                        description
                    )
                } // resumeViewModel::updateExperience
            )
        }
        composable(Routes.PREVIEW) {
            PreviewScreen(
                uiState = resumeViewModel.uiState,
                onEdit = {
                    navController.popBackStack(Routes.PERSONAL, inclusive = false)
                },
                onDelete = {
                    resumeViewModel.deleteAll {
                        navController.popBackStack(Routes.PERSONAL, inclusive = false)
                    }
                },
                onSaveDatabase = {
                    resumeViewModel.saveToFirebase()  // resumeViewModel::saveToFirebase
                },
                onDownloadPDF = {
                    resumeViewModel.downloadPDF(it)  // resumeViewModel::downloadPDF
                }
            )
        }
    }
}