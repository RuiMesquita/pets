package com.example.pets.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pets.presentation.navigation.Screens
import com.example.pets.presentation.screens.addEvent.AddEventScreen
import com.example.pets.presentation.screens.addEvent.AddEventViewModel
import com.example.pets.presentation.screens.addMedication.AddMedicationScreen
import com.example.pets.presentation.screens.addMedication.AddMedicationViewModel
import com.example.pets.presentation.screens.addPet.AddPetScreen
import com.example.pets.presentation.screens.addPet.AddPetViewModel
import com.example.pets.presentation.screens.petProfile.PetProfileScreen
import com.example.pets.presentation.screens.petProfile.PetProfileViewModel
import com.example.pets.presentation.screens.pets.PetsScreen
import com.example.pets.presentation.screens.pets.PetsViewModel
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screens.Pets.route) {

                composable(Screens.Pets.route) {
                    val petsViewModel: PetsViewModel = hiltViewModel()

                    PetsScreen(navController, petsViewModel)
                }

                composable(Screens.AddPet.route) {
                    val addPetViewModel: AddPetViewModel = hiltViewModel()
                    val state by addPetViewModel.state.collectAsState()
                    val validationEvents = addPetViewModel.validationEvents

                    AddPetScreen(navController, state, addPetViewModel::onEvent, validationEvents)
                }

                composable(
                    route = Screens.PetProfile.route + "/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) {
                    val petProfileViewModel: PetProfileViewModel = hiltViewModel()
                    val petState by petProfileViewModel.petRegistrationState.collectAsState()

                    PetProfileScreen(navController, petState, petProfileViewModel::onEvent)
                }

                composable(
                    route = Screens.AddEvent.route + "/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) {
                    val addEventViewModel: AddEventViewModel = hiltViewModel()
                    val eventState by addEventViewModel.state.collectAsState()
                    val validationEvent = addEventViewModel.validationEvents

                    AddEventScreen(navController, eventState, addEventViewModel::onEvent, validationEvent)
                }

                composable(
                    route = Screens.AddMedication.route + "/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType})
                ) {
                    val addMedicationViewModel: AddMedicationViewModel = hiltViewModel()
                    val medicationState by addMedicationViewModel.state.collectAsState()
                    val validationEvent = addMedicationViewModel.validationEvents

                    AddMedicationScreen(navController, medicationState, addMedicationViewModel::onEvent, validationEvent)
                }
            }
        }
    }
}