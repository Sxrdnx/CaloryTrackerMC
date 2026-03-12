package com.sxrdnx.onboarding_presentation.nutrient_goal

sealed class NutrientGoalEvent {
    data class OnCarbsRatioEnter(val ratio : String): NutrientGoalEvent()
    data class OnProteinRatio(val ratio: String): NutrientGoalEvent()
    data class OnFatRatioEnter(val ratio: String): NutrientGoalEvent()
    object OnNextClick: NutrientGoalEvent()
}