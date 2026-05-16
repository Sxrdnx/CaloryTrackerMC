package com.sxrdnx.tracker_presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sxrdnx.core.domain.use_case.FilterOutDigits
import com.sxrdnx.tracker_domain.use_case.TrackerUseCases
import javax.inject.Inject
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel

import com.sxrdnx.core.util.UiEvent
import com.sxrdnx.core.util.UiText
import com.sxrdnx.core.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigits: FilterOutDigits
): ViewModel() {
    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.OnQueryChange ->{
                state = state.copy(query = event.query)
            }
            is SearchEvent.OnAmountForFoodChange -> {
                state = state.copy(
                    trackableFood = state.trackableFood.map {
                        if (it.food == event.food){
                            it.copy(amount =  filterOutDigits(event.amount))
                        }else it
                    }
                )
            }
            SearchEvent.OnSearch -> {
                executeSearch()
            }
            is SearchEvent.OnToggleTrackableFood -> {
                state = state.copy(
                    trackableFood = state.trackableFood.map {
                        if (it.food == event.food)
                            it.copy(isExpanded = !it.isExpanded)
                        else
                            it
                    }
                )
            }
            is SearchEvent.OnSearchFocusChange -> {
                state =state.copy(
                    isHintVisible =  event.isFocused && state.query.isBlank()
                )
            }
            is SearchEvent.OnTrackFoodClick ->{
                trackFood(event)
            }
        }

    }

    private fun executeSearch() {
        viewModelScope.launch {
            state = state.copy(
                isSearching = true,
                trackableFood = emptyList()
            )

            trackerUseCases
                .searchFood(query = state.query)
                .onSuccess { foods ->
                    state = state.copy(
                        trackableFood = foods.map {
                            TrackableFoodUiState(it)
                        },
                        isSearching = false,
                        query = ""
                    )
                }
                .onFailure {
                    state = state.copy(isSearching = false)
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            UiText.StringResource(R.string.error_something_went_wrong)
                        )
                    )
                }
        }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodClick) {
       viewModelScope.launch {
           val uiState = state.trackableFood.find {
               it.food == event.food
           }
           trackerUseCases.trackFood(
               food = uiState?.food ?: return@launch,
               amount = uiState.amount.toIntOrNull() ?: return@launch,
               mealType = event.mealType,
               date = event.date
           )

           _uiEvent.send(UiEvent.NavigationUp)

       }
    }

}