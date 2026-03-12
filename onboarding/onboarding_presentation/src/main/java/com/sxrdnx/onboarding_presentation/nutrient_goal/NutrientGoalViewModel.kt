package com.sxrdnx.onboarding_presentation.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import com.sxrdnx.core.domain.preferences.Preferences
import com.sxrdnx.core.domain.use_case.FilterOutDigits
import com.sxrdnx.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits,

){
    var state by mutableStateOf(NutrientGoalState())
        set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent){
        when(event){
            is NutrientGoalEvent.OnCarbsRatioEnter ->{
                state = state.copy(
                    carbsRadio = filterOutDigits(event.ratio)
                )
            }
            is NutrientGoalEvent.OnProteinRatio -> {
                state = state.copy(
                    proteinRatio = filterOutDigits(event.ratio)
                )
            }

            is NutrientGoalEvent.OnFatRatioEnter -> {
                state = state.copy(
                    fatRatio = filterOutDigits(event.ratio)
                )
            }

            NutrientGoalEvent.OnNextClick ->{

            }
        }
    }

}