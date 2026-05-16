package com.sxrdnx.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_ui.LocalSpacing
import com.sxrdnx.core.util.UiEvent
import com.sxrdnx.core.R
import com.sxrdnx.tracker_presentation.tracker_overview.components.AddButton
import com.sxrdnx.tracker_presentation.tracker_overview.components.DaySelector
import com.sxrdnx.tracker_presentation.tracker_overview.components.ExpandableMeal
import com.sxrdnx.tracker_presentation.tracker_overview.components.NutrientsHeader
import com.sxrdnx.tracker_presentation.tracker_overview.components.TrackedFoodItem

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UiEvent.Navigate)-> Unit,
    viewmodel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewmodel.state
    val context = LocalContext.current
    LaunchedEffect(key1 =context ) {
        viewmodel.uiEvent.collect{event ->

            when(event){
                is UiEvent.Navigate -> onNavigate(UiEvent.Navigate(event.route))
               else -> Unit
            }
        }
        
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    )
    {
        item {
            NutrientsHeader(state = state)

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            DaySelector(
                date = state.date,
                onPreviousDayClick = {
                    viewmodel.onEvent(TrackerOverViewEvent.OnPreviousDayClick)
                },
                onNextDayClick = {
                    viewmodel.onEvent(TrackerOverViewEvent.OnNextDayClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }
        items(state.meals){meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = {
                    viewmodel.onEvent(TrackerOverViewEvent.OnToggleMealClick(meal))
                },
                content = {
                          Column(
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .padding(horizontal = spacing.spaceSmall)
                          ) {
                              state.trackedFoods.forEach {food ->
                                  TrackedFoodItem(
                                      trackedFood = food,
                                      onDeleteClick = {
                                          viewmodel.onEvent(
                                              TrackerOverViewEvent.OnDeleteTrackedFoodClick(food)
                                          )
                                      }
                                  )
                                  Spacer(modifier = Modifier.height(spacing.spaceMedium))
                              }
                              AddButton(
                                  text = stringResource(id = R.string.add_meal,meal.name.asString(context)) ,
                                  onClick = {
                                      viewmodel.onEvent(
                                          TrackerOverViewEvent.OnAddFoodClick(meal)
                                      )

                                  },
                                  modifier = Modifier.fillMaxWidth()
                                  )
                          }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}