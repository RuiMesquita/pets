package com.example.pets.presentation.navigation

sealed class Screens(val route: String) {
    object Pets: Screens("pets")
    object PetProfile: Screens("pet_profile")
    object AddPet: Screens("add_pet")
}
