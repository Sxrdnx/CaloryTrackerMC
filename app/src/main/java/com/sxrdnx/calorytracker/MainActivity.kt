package com.sxrdnx.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sxrdnx.calorytracker.navigation.navigate
import com.sxrdnx.calorytracker.ui.theme.CaloryTrackerTheme
import com.sxrdnx.core.navigation.Route
import com.sxrdnx.onboarding_presentation.activity.ActivityScreen
import com.sxrdnx.onboarding_presentation.age.AgeScreen
import com.sxrdnx.onboarding_presentation.gender.GenderScreen
import com.sxrdnx.onboarding_presentation.goal.GoalScreen
import com.sxrdnx.onboarding_presentation.height.HeightScreen
import com.sxrdnx.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.sxrdnx.onboarding_presentation.weight.WeightScreen
import com.sxrdnx.onboarding_presentation.welcome.WelcomeScreen
import com.sxrdnx.tracker_presentation.search.SearchScreen
import com.sxrdnx.tracker_presentation.tracker_overview.TrackerOverviewScreen
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
                ) { it ->
                    it
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

                        composable(Route.ACTIVITY){
                            ActivityScreen(onNavigate =navController::navigate )

                        }

                        composable(Route.GOAL){
                            GoalScreen(onNavigate = navController::navigate)

                        }

                        composable(Route.NUTRIENT_GOAL){
                            NutrientGoalScreen(scaffoldState = scaffoldState, onNavigate = navController::navigate)
                        }

                        composable(Route.TRACKER_OVERVIEW){
                            TrackerOverviewScreen(onNavigate = navController::navigate)

                        }

                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName"){
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth"){
                                    type = NavType.IntType
                                },
                                navArgument("month"){
                                    type = NavType.IntType
                                },
                                navArgument("year"){
                                    type = NavType.IntType
                                },
                            )

                        ){ navBackStackEtry ->
                            val mealName = navBackStackEtry.arguments?.getString("mealName")!!
                            val dayOfMonth = navBackStackEtry.arguments?.getInt("dayOfMonth")!!
                            val month = navBackStackEtry.arguments?.getInt("month")!!
                            val year = navBackStackEtry.arguments?.getInt("year")!!

                            SearchScreen(
                                scaffoldState =scaffoldState ,
                                mealName =mealName ,
                                dayOfMonth =dayOfMonth ,
                                month = month,
                                year = year,
                                onNavigateUp = {
                                    navController.navigateUp()
                                })

                        }




                    }

                }



            }
        }
    }
}