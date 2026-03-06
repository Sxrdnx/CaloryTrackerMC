package com.sxrdnx.onboarding_presentation.goal



import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sxrdnx.core.domain.preferences.Preferences
import com.sxrdnx.core.navigation.Route
import com.sxrdnx.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.sxrdnx.core.domain.model.ActivityLevel
import com.sxrdnx.core.domain.model.GoalType

@HiltViewModel
class GoalViewmodel @Inject constructor(
    private val preferences: Preferences,
): ViewModel() {

    var selectedGoal  by mutableStateOf<GoalType>(GoalType.KeepWeight)
        private set

    //chenel use for send a one time events to ui our composble
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalTypeSelected(goalType: GoalType){
        selectedGoal = goalType
    }

    fun onNextClick(){
        viewModelScope.launch {
            preferences.saveGoalType(selectedGoal)
            _uiEvent.send(UiEvent.Navigate(Route.NUTRIENT_GOAL))
        }
    }
}