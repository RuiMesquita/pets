package com.example.pets

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pets.presentation.navigation.Screens
import com.example.pets.presentation.screens.add_pet.AddPetScreen
import com.example.pets.presentation.screens.add_pet.AddPetViewModel
import com.example.pets.presentation.screens.pets.PetsScreen
import com.example.pets.presentation.screens.pets.PetsViewModel
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val addPetViewModel: AddPetViewModel = hiltViewModel()
            val petsViewModel: PetsViewModel = hiltViewModel()

            val pets by petsViewModel.pets.collectAsState()
            val state by addPetViewModel.state.collectAsState()

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screens.Pets.route) {
                composable(Screens.Pets.route) {
                    PetsScreen(navController, pets)
                }
                composable(Screens.AddPet.route) {
                    AddPetScreen(navController, state, addPetViewModel::onEvent)
                }
            }
        }
    }
}