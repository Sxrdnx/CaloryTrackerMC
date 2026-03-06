package com.sxrdnx.onboarding_presentation.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_ui.LocalSpacing
import com.sxrdnx.core.util.UiEvent
import com.sxrdnx.core.R
import com.sxrdnx.core.domain.model.ActivityLevel
import com.sxrdnx.core.domain.model.Gender
import com.sxrdnx.core.domain.model.GoalType
import com.sxrdnx.onboarding_presentation.components.ActionButton
import com.sxrdnx.onboarding_presentation.components.SelectableButton

@Composable
fun GoalScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewmodel: GoalViewmodel = hiltViewModel()
){
    val spacing = LocalSpacing.current
    LaunchedEffect(key1 = true) {
        viewmodel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.Navigate -> onNavigate(event)
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
                text = stringResource(id = R.string.lose_keep_or_gain_weight),
                style = MaterialTheme.typography.h3
                )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Row {
                SelectableButton(
                    text = stringResource(id = R.string.lose),
                    isSelected = viewmodel.selectedGoal is GoalType.LoseWeight,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White ,
                    onClick = {
                        viewmodel.onGoalTypeSelected(goalType = GoalType.LoseWeight)
                    },
                    textStyle = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Normal
                    )
                )

                Spacer(modifier = Modifier.width(spacing.spaceMedium))

                SelectableButton(
                    text = stringResource(id = R.string.keep),
                    isSelected = viewmodel.selectedGoal is GoalType.KeepWeight ,
                    color = MaterialTheme.colors.primaryVariant ,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewmodel.onGoalTypeSelected(GoalType.KeepWeight)
                    },
                    textStyle = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Normal
                    )
                )


                Spacer(modifier = Modifier.width(spacing.spaceMedium))

                SelectableButton(
                    text = stringResource(id = R.string.gain),
                    isSelected = viewmodel.selectedGoal is GoalType.GainWeight ,
                    color = MaterialTheme.colors.primaryVariant ,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewmodel.onGoalTypeSelected(GoalType.GainWeight)
                    },
                    textStyle = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = viewmodel::onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd)
            )
    }
}