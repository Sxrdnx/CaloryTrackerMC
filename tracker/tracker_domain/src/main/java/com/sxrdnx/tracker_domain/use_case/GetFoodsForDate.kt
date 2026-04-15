package com.sxrdnx.tracker_domain.use_case

import com.sxrdnx.tracker_domain.model.TrackableFood
import com.sxrdnx.tracker_domain.model.TrackedFood
import com.sxrdnx.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetFoodsForDate(
    private val repository: TrackerRepository
){
   operator fun invoke (date: LocalDate): Flow<List<TrackedFood>> {
      return repository.getFoodForDate(date)
  }
}