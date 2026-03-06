package com.sxrdnx.onboarding_presentation.gender



import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sxrdnx.core.domain.model.Gender
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
@HiltViewModel
class GenderViewmodel @Inject constructor(
    private val preferences: Preferences,
): ViewModel() {

    var selectedGender by mutableStateOf<Gender>(Gender.Male) // this only change value into viewmodel
        private set

    //chenel use for send a one time events to ui our composble
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onClickGender(gender: Gender){
        selectedGender = gender
    }

    fun onNextClick(){
        viewModelScope.launch {
            preferences.saveGender(selectedGender)
            _uiEvent.send(UiEvent.Navigate(Route.AGE))
        }
    }
}