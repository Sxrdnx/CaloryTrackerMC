package com.sxrdnx.tracker_domain.use_case

import com.sxrdnx.tracker_domain.model.TrackableFood
import com.sxrdnx.tracker_domain.model.TrackedFood
import com.sxrdnx.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class DeleteTrackedFood(
    private val repository: TrackerRepository
){
  suspend operator fun invoke (trackedFood: TrackedFood) {
      repository.deleteTrackedFood(food = trackedFood)

  }
}