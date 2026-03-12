package com.sxrdnx.onboarding_presentation.nutrient_goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_ui.LocalSpacing
import com.sxrdnx.core.R
import com.sxrdnx.core.util.UiEvent
import com.sxrdnx.onboarding_presentation.components.ActionButton
import com.sxrdnx.onboarding_presentation.components.UnitTextField

@Composable
fun NutrientGoalScreen(
    scaffoldState: ScaffoldState,
    onNavigate:(UiEvent.Navigate)->Unit,
    viewmodel: NutrientGoalViewModel = hiltViewModel()
){
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewmodel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.menssage.asString(context)
                    )
                }
                else -> Unit
            }
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge),
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment =  Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.h3
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            UnitTextField(
                value = viewmodel.state.carbsRadio,
                onValueChange = {
                    viewmodel.onEvent(NutrientGoalEvent.OnCarbsRatioEnter(it))
                },
                unit = stringResource(id = R.string.percent_carbs),
                modifier = Modifier
            )
            UnitTextField(
                value = viewmodel.state.proteinRatio,
                onValueChange = {
                    viewmodel.onEvent(NutrientGoalEvent.OnProteinRatio(it))
                },
                unit = stringResource(id = R.string.percent_proteins),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewmodel.state.fatRatio,
                onValueChange = {
                    viewmodel.onEvent(NutrientGoalEvent.OnFatRatioEnter(it))
                },
                unit = stringResource(id = R.string.percent_fats),
                modifier = Modifier
            )

        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                viewmodel.onEvent(NutrientGoalEvent.OnNextClick)
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }

}