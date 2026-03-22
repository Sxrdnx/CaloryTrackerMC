package com.sxrdnx.tracker_domain.repository

import com.sxrdnx.tracker_domain.model.TrackableFood
import com.sxrdnx.tracker_domain.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TrackerRepository {

    suspend fun searchFood(
        query:String,
        page:Int,
        pageSize: Int,
    ): Result<List<TrackableFood>>


    suspend fun insertTrackedFood(food: TrackedFood)

    suspend fun deleteTrackedFood(food: TrackedFood)

    suspend fun getFoodForDate(localDate: LocalDate): Flow<List<TrackedFood>>
}