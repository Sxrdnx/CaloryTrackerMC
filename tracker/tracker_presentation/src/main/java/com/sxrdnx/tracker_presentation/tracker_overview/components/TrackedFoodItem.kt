package com.sxrdnx.tracker_presentation.tracker_overview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core_ui.LocalSpacing
import com.sxrdnx.tracker_domain.model.TrackedFood
import com.sxrdnx.core.R
import com.sxrdnx.tracker_presentation.components.NutrientInfo

@Composable
fun TrackedFoodItem(
    trackedFood: TrackedFood,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(spacing.spaceExctraSmall)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(MaterialTheme.colors.surface)
            .padding(end = spacing.spaceMedium)
            ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //image
      AsyncImage(
          model = ImageRequest.Builder(context)
          .data(trackedFood.imageUrl)
          .crossfade(true)
          .build(),
          contentDescription = trackedFood.name,
          contentScale = ContentScale.Crop,
          placeholder = painterResource(id = R.drawable.ic_burger),
          error = painterResource(id = R.drawable.ic_burger),
          fallback = painterResource(id = R.drawable.ic_burger),
          modifier = Modifier
              .size(100.dp)
              .aspectRatio(1f)
              .clip(
                  RoundedCornerShape(
                      topStart = 5.dp,
                      bottomEnd = 5.dp
                  )
              )
        )

        Spacer(modifier = Modifier.width(spacing.spaceMedium))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = trackedFood.name,
                style = MaterialTheme.typography.body1,
                overflow =  TextOverflow.Ellipsis,
                maxLines = 2,
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            Text(
                text = stringResource(
                    id = R.string.nutrient_info,
                    trackedFood.amount,
                    trackedFood.calories
                ),
                style = MaterialTheme.typography.body2
            )


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ){

                Spacer(modifier = Modifier.height(spacing.spaceExctraSmall))
                Row{
                    NutrientInfo(
                        name = stringResource(id = R.string.carbs) ,
                        amount = trackedFood.carbs ,
                        unit = stringResource(id = R.string.grams),
                        amountTextSize = 16.sp,
                        unitTextSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        nameTextStyle = MaterialTheme.typography.body2
                        )

                    Spacer(modifier = Modifier.width(spacing.spaceExctraSmall))
                    NutrientInfo(
                        name = stringResource(id = R.string.protein) ,
                        amount = trackedFood.protein,
                        unit = stringResource(id = R.string.grams),
                        amountTextSize = 16.sp,
                        unitTextSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        nameTextStyle = MaterialTheme.typography.body2
                    )

                    Spacer(modifier = Modifier.width(spacing.spaceExctraSmall))
                    NutrientInfo(
                        name = stringResource(id = R.string.fat),
                        amount = trackedFood.fat,
                        unit = stringResource(id = R.string.grams),
                        amountTextSize = 16.sp,
                        unitTextSize = 12.sp,
                        modifier = Modifier.weight(1f),
                        nameTextStyle = MaterialTheme.typography.body2
                    )
                }
            }
        }

        Icon(imageVector = Icons.Default.Close,
            contentDescription = stringResource(id = R.string.delete),
            modifier = Modifier
                .align(Alignment.Top)
                .padding(top = 3.dp, start = 3.dp )
                .clickable { onDeleteClick() }
        )
    }
}