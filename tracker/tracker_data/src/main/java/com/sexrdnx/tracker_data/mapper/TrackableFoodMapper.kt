package com.sexrdnx.tracker_data.mapper

import com.sexrdnx.tracker_data.remote.dto.Product
import com.sxrdnx.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun Product.toTrackableFood(): TrackableFood?{
    val nutriments = nutriments ?: return null

    return TrackableFood(
        name = productName.orEmpty(),
        carbsPer100g = nutriments.carbohydrates100g.roundToInt(),
        caloriesPer100g = nutriments.energyKcal100g.roundToInt(),
        proteinPer100g = nutriments.proteins100g.roundToInt(),
        fatPer100g = nutriments.fat100g.roundToInt() ,
        imageUrl = imageFromThumbUrl
    )

}