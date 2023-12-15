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
import com.example.pets.presentation.screens.add_event.AddEventScreen
import com.example.pets.presentation.screens.add_event.AddEventViewModel
import com.example.pets.presentation.screens.add_medication.AddMedicationScreen
import com.example.pets.presentation.screens.add_medication.AddMedicationViewModel
import com.example.pets.presentation.screens.add_pet.AddPetScreen
import com.example.pets.presentation.screens.add_pet.AddPetViewModel
import com.example.pets.presentation.screens.pet_profile.PetProfileScreen
import com.example.pets.presentation.screens.pet_profile.PetProfileViewModel
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

                    AddPetScreen(navController, state, addPetViewModel::onEvent)
                }

                composable(
                    route = Screens.PetProfile.route + "/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) {
                    val petProfileViewModel: PetProfileViewModel = hiltViewModel()
                    val petState by petProfileViewModel.petState.collectAsState()

                    PetProfileScreen(navController, petState, petProfileViewModel::onEvent)
                }

                composable(
                    route = Screens.AddEvent.route + "/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) {
                    val addEventViewModel: AddEventViewModel = hiltViewModel()
                    val eventState by addEventViewModel.state.collectAsState()

                    AddEventScreen(navController, eventState, addEventViewModel::onEvent)
                }

                composable(
                    route = Screens.AddMedication.route + "/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType})
                ) {
                    val addMedicationViewModel: AddMedicationViewModel = hiltViewModel()
                    val medicationState by addMedicationViewModel.state.collectAsState()

                    AddMedicationScreen(navController, medicationState, addMedicationViewModel::onEvent)
                }
            }
        }
    }
}