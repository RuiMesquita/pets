package com.example.pets.presentation.screens.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pets.R
import com.example.pets.common.Constants.DAY_SUFFIXES
import com.example.pets.presentation.screens.common.TopBar
import com.example.pets.presentation.ui.theme.DefaultGrey
import com.example.pets.presentation.ui.theme.WashedWhite
import com.example.pets.presentation.ui.theme.customTypography


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    navController: NavController,
    viewModel: CalendarViewModel
) {
    val events by viewModel.calendarEvent.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WashedWhite)
            .padding(15.dp)
    ) {

        TopBar(
            displayRightButton = false,
            leftIcon = R.drawable.arrow_back,
            leftButtonOnClickAction = { navController.navigateUp() },
            title = "Upcoming"
        )

        Spacer(modifier = Modifier.height(20.dp))

        val groupedEvents = events.groupBy { event -> event.date }
        Column {
            groupedEvents.forEach { (date, eventOnSameDay) ->
                DateBar(
                    month = "${date.month}",
                    day = "${date.dayOfMonth}${DAY_SUFFIXES[date.dayOfMonth]}"
                )
                Spacer(Modifier.height(20.dp))
                eventOnSameDay.forEach { event ->
                    UpcomingEvent(
                        eventName = event.eventName,
                        petPhoto = event.photo,
                        hour = "${event.time}"
                    )
                    Spacer(Modifier.height(20.dp)) // Adjust space between events
                }
            }
        }

    }
}

@Composable
fun DateBar(
    month: String,
    day: String
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Divider(modifier = Modifier.width(115.dp), color = Color.Black)
        Text(
            text = String.format("%s %s", month.take(3), day),
            textAlign = TextAlign.Center,
            style = customTypography.titleSmall
        )
        Divider(modifier = Modifier.width(115.dp), color = Color.Black)
    }
}


@Composable
fun UpcomingEvent(
    eventName: String,
    petPhoto: String?,
    hour: String,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(DefaultGrey, RoundedCornerShape(10.dp))
            .height(80.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = petPhoto,
                contentDescription = "pet_photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
            )

            Column (
                modifier = Modifier
                    .weight(3f)
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = eventName,
                    style = customTypography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = hour,
                    style = customTypography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}