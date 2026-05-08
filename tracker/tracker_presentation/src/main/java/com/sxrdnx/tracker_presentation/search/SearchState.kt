package com.sxrdnx.tracker_presentation.search

import com.sxrdnx.tracker_domain.model.TrackableFood

data class SearchState(
    val query: String = "",
    val isHintVisible: Boolean  = false,
    val isSearching: Boolean = false,
    val trackableFood: List<TrackableFoodUiState> = emptyList()
)
