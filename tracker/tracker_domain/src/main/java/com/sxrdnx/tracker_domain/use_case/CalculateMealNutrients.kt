package com.sxrdnx.tracker_domain.use_case

import com.sxrdnx.core.domain.model.ActivityLevel
import com.sxrdnx.core.domain.model.Gender
import com.sxrdnx.core.domain.model.GoalType
import com.sxrdnx.core.domain.model.UserInfo
import com.sxrdnx.core.domain.preferences.Preferences
import com.sxrdnx.tracker_domain.model.MealType
import com.sxrdnx.tracker_domain.model.TrackableFood
import com.sxrdnx.tracker_domain.model.TrackedFood
import com.sxrdnx.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import kotlin.math.roundToInt

class CalculateMealNutrients(
    private val preferences: Preferences
){
    operator fun invoke(trackedFoods: List<TrackedFood>): ResultMacros{
        val allNutrients = trackedFoods
            .groupBy { it.mealType }
            .mapValues { mealNutrientsMap ->
                val type = mealNutrientsMap.key
                val foods = mealNutrientsMap.value
                MealNutrients(
                    carbs = foods.sumOf { it.carbs },
                    protein = foods.sumOf { it.protein },
                    fat = foods.sumOf { it.fat },
                    calories = foods.sumOf { it.calories },
                    mealType = type
                )
            }

        val totalCarbs = allNutrients.values.sumOf { it.carbs }
        val totalProtein = allNutrients.values.sumOf { it.protein }
        val totalFat = allNutrients.values.sumOf { it.fat }
        val totalCalories = allNutrients.values.sumOf { it.calories }

        val userInfo = preferences.loadUserInfo()
        val caloryGoal = dailyCaloryRequirement(userInfo)
        val carbsGoal = (caloryGoal * userInfo.carbRatio / 4f).roundToInt()
        val proteinGoal = (caloryGoal * userInfo.proteinRatio / 4f).roundToInt()
        val fatGoal = (caloryGoal * userInfo.fatRatio / 9f).roundToInt()

        return ResultMacros(
            carbsGoal = carbsGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloryGoal,
            totalCarbs = totalCarbs,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients
        )
    }

    private fun dailyCaloryRequirement(userInfo: UserInfo): Int {
        val activityFactor = when (userInfo.activityLevel){
            ActivityLevel.High -> 1.4f
            ActivityLevel.Medium -> 1.3f
            ActivityLevel.Low -> 1.2f
        }

        val caloryExtra = when(userInfo.goalType){
            GoalType.GainWeight -> -500
            GoalType.KeepWeight -> 0
            GoalType.LoseWeight -> 500
        }

        return (bmr(userInfo) * activityFactor + caloryExtra).roundToInt()
    }

    private fun bmr(userInfo: UserInfo): Int {
        return when(userInfo.gender) {
            is Gender.Male -> {
                (66.47f + 13.75f * userInfo.weight +
                        5f * userInfo.height - 6.75f * userInfo.age).roundToInt()
            }
            is Gender.Female ->  {
                (665.09f + 9.56f * userInfo.weight +
                        1.84f * userInfo.height - 4.67 * userInfo.age).roundToInt()
            }
        }
    }


    data class ResultMacros(
        val carbsGoal: Int,
        val proteinGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val mealNutrients: Map<MealType,MealNutrients>
    )

    data class MealNutrients(
        val carbs: Int,
        val protein: Int,
        val fat: Int,
        val calories: Int,
        val mealType: MealType
    )

}