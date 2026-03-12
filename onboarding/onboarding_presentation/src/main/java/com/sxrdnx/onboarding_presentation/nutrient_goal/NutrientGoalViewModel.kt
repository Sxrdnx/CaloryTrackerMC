package com.sxrdnx.onboarding_presentation.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sxrdnx.core.domain.preferences.Preferences
import com.sxrdnx.core.domain.use_case.FilterOutDigits
import com.sxrdnx.core.util.UiEvent
import com.sxrdnx.onboarding_domain.use_case.ValidateNutrients
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.sxrdnx.core.navigation.Route
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits,
    private val validateNutrients: ValidateNutrients
): ViewModel(){
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
                val result = validateNutrients(
                    carbsRatioText = state.carbsRadio,
                    proteinRatioText = state.proteinRatio,
                    fatRatioText =  state.fatRatio
                )

                when(result){
                    is ValidateNutrients.Result.Error ->{
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackBar(result.message))
                        }
                    }

                    is ValidateNutrients.Result.Success->{
                        preferences.saveCarbRatio(result.carbsRatio)
                        preferences.saveProteinRatio(result.proteinRatio)
                        preferences.saveFatRatio(result.fatRatio)
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.Navigate(Route.TRACKER_OVERVIEW))
                        }
                    }
                }
            }
        }
    }

}