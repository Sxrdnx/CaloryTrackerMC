package com.sxrdnx.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sxrdnx.calorytracker.navigation.navigate
import com.sxrdnx.calorytracker.ui.theme.CaloryTrackerTheme
import com.sxrdnx.core.navigation.Route
import com.sxrdnx.onboarding_presentation.age.AgeScreen
import com.sxrdnx.onboarding_presentation.gender.GenderScreen
import com.sxrdnx.onboarding_presentation.height.HeightScreen
import com.sxrdnx.onboarding_presentation.weight.WeightScreen
import com.sxrdnx.onboarding_presentation.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloryTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) { it
                    NavHost(navController = navController, startDestination = Route.WELCOME) {
                        composable(Route.WELCOME){
                            WelcomeScreen(onNavigate = navController::navigate)
                        }

                        composable(Route.AGE){
                            AgeScreen(scaffoldState = scaffoldState, onNavigate = navController::navigate)
                        }

                        composable(Route.GENDER){
                            GenderScreen(onNavigate = navController::navigate)

                        }

                        composable(Route.HEIGHT){
                            HeightScreen(scaffoldState = scaffoldState, onNavigate = navController::navigate)
                        }

                        composable(Route.WEIGHT){
                            WeightScreen(scaffoldState = scaffoldState, onNavigate = navController::navigate)

                        }

                        composable(Route.NUTRIENT_GOAL){

                        }

                        composable(Route.ACTIVITY){

                        }


                        composable(Route.GOAL){

                        }

                        composable(Route.TRACKER_OVERVIEW){

                        }

                        composable(Route.SEARCH){

                        }




                    }

                }



            }
        }
    }
}