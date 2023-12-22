package com.example.pets.presentation.screens.addEvent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pets.R
import com.example.pets.presentation.screens.ValidationEvent
import com.example.pets.presentation.screens.addPet.YSpacing15Dp
import com.example.pets.presentation.screens.common.TopBar
import com.example.pets.presentation.ui.theme.PrimaryYellow
import com.example.pets.presentation.ui.theme.WashedWhite
import com.example.pets.presentation.ui.theme.customTypography
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    navController: NavController,
    state: EventState,
    onEvent: (EventEvent) -> Unit,
    validationEvent: Flow<ValidationEvent>
) {

    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        validationEvent.collect { event ->
            when(event) {
                is ValidationEvent.Success -> {
                    onEvent(EventEvent.SaveEvent)
                    onEvent(EventEvent.ResetEvent)
                    navController.popBackStack()
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(WashedWhite)
            .padding(15.dp)
            .fillMaxSize()
    ) {
        TopBar(
            title = "Add Events",
            leftIcon = R.drawable.arrow_back,
            leftButtonOnClickAction = { navController.popBackStack() },
            displayRightButton = false
        )

        YSpacing15Dp()
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.eventName,
            onValueChange = { onEvent(EventEvent.SetNameEvent(it)) },
            placeholder = { Text(text = "Event name", style = customTypography.bodyLarge, color = Color(0xFFC0C0C0)) },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            isError = state.nameError != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFFC0C0C0),
                focusedBorderColor = PrimaryYellow
            ),
        )
        if (state.nameError != null) {
            Text(
                text = state.nameError,
                color = Color.Red
            )
        }

        YSpacing15Dp()
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.eventDate,
            onValueChange = { onEvent(EventEvent.SetEventDate(it))},
            placeholder = { Text(text = "Event date", style = customTypography.bodyLarge, color = Color(0xFFC0C0C0)) },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            isError = state.dateError != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFFC0C0C0),
                focusedBorderColor = PrimaryYellow
            ),
        )
        if (state.dateError != null) {
            Text(
                text = state.dateError,
                color = Color.Red
            )
        }

        YSpacing15Dp()
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.eventHour,
            onValueChange = { onEvent(EventEvent.SetEventHour(it)) },
            placeholder = { Text(text = "Event hour", style = customTypography.bodyLarge, color = Color(0xFFC0C0C0)) },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            isError = state.hourError != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFFC0C0C0),
                focusedBorderColor = PrimaryYellow
            ),
        )
        if (state.hourError != null) {
            Text(
                text = state.hourError,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Save button
        Button(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            onClick = {
                onEvent(EventEvent.ValidateEvent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryYellow)
        ) {
            Text(text = "save", style = customTypography.titleSmall, color = Color.White)
        }
    }
}
