package com.sxrdnx.calorytracker.navigation

import androidx.navigation.NavController
import com.sxrdnx.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate){
    this.navigate(event.route)
}