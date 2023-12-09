package com.example.pets.presentation.screens.pet_profile


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.pets.R
import com.example.pets.common.Constants.daySuffixes
import com.example.pets.domain.model.toEventEntity
import com.example.pets.domain.model.toMedicationEntity
import com.example.pets.presentation.navigation.Screens
import com.example.pets.presentation.screens.common.TopBar
import com.example.pets.presentation.ui.theme.Blue
import com.example.pets.presentation.ui.theme.DarkRed
import com.example.pets.presentation.ui.theme.DefaultGrey
import com.example.pets.presentation.ui.theme.Green
import com.example.pets.presentation.ui.theme.PrimaryYellow
import com.example.pets.presentation.ui.theme.Purple
import com.example.pets.presentation.ui.theme.Red
import com.example.pets.presentation.ui.theme.SecondaryYellow
import com.example.pets.presentation.ui.theme.WashedWhite
import com.example.pets.presentation.ui.theme.customTypography
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import kotlin.reflect.KFunction2


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PetProfileScreen (
    navController: NavController,
    petState: PetState,
    onEvent: (PetProfileEvent) -> Unit
) {
    var openAlertDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    // Alert Dialogs for pet deletion
    when {
        openAlertDialog -> {
            DialogAlert(
                onDismissRequest = { openAlertDialog = false },
                onConfirmation = {
                    onEvent(PetProfileEvent.DeletePet)
                    navController.popBackStack()
                },
                title = "Delete pet",
                text = "Are you sure you want to delete this pet profile ?",
                icon = Icons.Default.Delete
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(WashedWhite)
            .fillMaxSize()
            .padding(15.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TopBar(
            title = petState.name,
            leftIcon = R.drawable.arrow_back,
            rightIcon = R.drawable.menu,
            leftButtonOnClickAction = { navController.popBackStack() },
            rightButtonOnClickAction = { expanded = !expanded }
        )

        Box(
            contentAlignment = Alignment.TopEnd,
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(x = 46.dp, y = 0.dp)
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Schedule events", style = customTypography.bodyMedium) },
                    onClick = { navController.navigate(Screens.AddEvent.route + "/${petState.id}") },
                )
                Divider()
                DropdownMenuItem(
                    text = { Text(text = "Add Medication", style = customTypography.bodyMedium) },
                    onClick = { navController.navigate(Screens.AddMedication.route + "/${petState.id}") }
                )
                Divider()
                DropdownMenuItem(
                    text = { Text(text = "Delete Pet", style = customTypography.bodyMedium) },
                    onClick = {
                        openAlertDialog = true
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        AsyncImage(
            model = petState.photo,
            contentScale = ContentScale.Crop,
            contentDescription = "pet photo",
            error = painterResource(id = R.drawable.image),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row (
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ){
            InfoBoxes(
                title = petState.gender.toString(),
                body = "Sex",
                color = Green,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            InfoBoxes(
                title = "${petState.age} Years",
                body = "Age",
                color = Blue,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            InfoBoxes(
                title = "${petState.weight} Kg",
                body = "Weight",
                color = SecondaryYellow,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Events",
            style = customTypography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
        )

        petState.events.forEach { event ->
            Spacer(modifier = Modifier.height(14.dp))
            SwipeableActionsBox(
                endActions = listOf(
                    deleteEvent {
                        onEvent(PetProfileEvent.SetDeleteEventItem(event.toEventEntity()))
                        onEvent(PetProfileEvent.DeleteEvent)
                    }
                ),
                swipeThreshold = 200.dp
            ) {
                EventItem(
                    eventName = event.name,
                    day = "${event.date.dayOfMonth}${daySuffixes[event.date.dayOfMonth]}",
                    month = "${event.date.month}",
                    hour = "${event.time}h"
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Medication",
            style = customTypography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
        )

        petState.medications.forEach { medication ->
            Spacer(modifier = Modifier.height(14.dp))
            SwipeableActionsBox(
                endActions = listOf(
                    deleteMedication {
                        onEvent(PetProfileEvent.SetDeleteMedicationItem(medication.toMedicationEntity()))
                        onEvent(PetProfileEvent.DeleteMedication)
                    }
                ),
                swipeThreshold = 200.dp
            ){
                MedicationItem(
                    medicationName = medication.name,
                    description = medication.description
                )
            }

        }
    }
}


@Composable
fun DialogAlert(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    title: String,
    text: String,
    icon: ImageVector
) {
    AlertDialog(
        confirmButton = {
            TextButton(
                onClick = { onConfirmation() }
            ) {
                Text(
                    text = "Confirm",
                    color = PrimaryYellow
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(
                    text = "Cancel",
                    color = PrimaryYellow
                )
            }
        },
        onDismissRequest = { onDismissRequest() },
        title = { Text(text = title, style = customTypography.titleMedium) },
        text = { Text(text = text, style = customTypography.bodyMedium) },
        icon = { Icon(icon, contentDescription = "Icon") }
    )
}


@Composable
fun EventItem(
    eventName: String,
    day: String,
    month: String,
    hour: String,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(DefaultGrey, RoundedCornerShape(16.dp))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(Red, RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = month,
                    style = customTypography.bodyMedium,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = day,
                    style = customTypography.bodyMedium,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        Column (
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = eventName,
                style = customTypography.bodyLarge,
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

@Composable
fun MedicationItem(
    medicationName: String,
    description: String
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(DefaultGrey, RoundedCornerShape(16.dp))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(Purple, RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))

        ) {
            Text(
                text = medicationName,
                style = customTypography.bodyMedium,
                fontWeight = FontWeight.Normal
            )
        }
        Column (
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = description,
                style = customTypography.bodyMedium,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun InfoBoxes(title: String, body: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(70.dp)
            .background(color, RoundedCornerShape(16.dp))
    ) {
        Column {
            Text(
                text = title,
                style = customTypography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = body,
                style = customTypography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

fun deleteMedication(onDelete: () -> Unit): SwipeAction {
    return SwipeAction(
        onSwipe = { onDelete() },
        icon = {
            Icon(
                modifier = Modifier.padding(10.dp),
                painter = painterResource(id = R.drawable.trash),
                contentDescription = null,
                tint = Color.White
            )
        },
        background = DarkRed
    )
}


fun deleteEvent(onDelete: () -> Unit): SwipeAction {
    return SwipeAction(
        onSwipe = { onDelete() },
        icon = {
            Icon(
                modifier = Modifier.padding(10.dp),
                painter = painterResource(id = R.drawable.trash),
                contentDescription = null,
                tint = Color.White
            )
        },
        background = DarkRed)
}