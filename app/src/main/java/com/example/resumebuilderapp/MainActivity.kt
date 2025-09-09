package com.example.resumebuilderapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import com.example.resumebuilderapp.navigation.AppNavHost
import com.example.resumebuilderapp.ui.theme.ResumeBuilderAppTheme

class MainActivity : ComponentActivity() {
//    val navController: NavHostController = NavHostController(
//        context = TODO()
//    )
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResumeBuilderAppTheme {
//                AppNavigation()
                AppNavHost()
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    AppNavi
//                }
            }
        }
    }
}

