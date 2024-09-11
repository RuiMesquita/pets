package com.example.pets.presentation.screens.addPet

import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pets.R
import com.example.pets.domain.model.enums.Gender
import com.example.pets.domain.model.enums.Species
import com.example.pets.presentation.screens.ValidationEvent
import com.example.pets.presentation.screens.common.TopBar
import com.example.pets.presentation.ui.theme.Blue
import com.example.pets.presentation.ui.theme.DarkerGrey
import com.example.pets.presentation.ui.theme.DefaultGrey
import com.example.pets.presentation.ui.theme.Pink
import com.example.pets.presentation.ui.theme.PrimaryYellow
import com.example.pets.presentation.ui.theme.WashedWhite
import com.example.pets.presentation.ui.theme.customTypography
import kotlinx.coroutines.flow.Flow


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(
    navHostController: NavHostController,
    state: AddPetState,
    onEvent: (PetEvent) -> Unit,
    validationEvent: Flow<ValidationEvent>
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        validationEvent.collect { event ->
            when(event) {
                is ValidationEvent.Success -> {
                    onEvent(PetEvent.SavePet)
                    onEvent(PetEvent.ResetPet)
                    navHostController.popBackStack()
                }
            }
        }
    }
    
    Column(
        modifier = Modifier
            .background(WashedWhite)
            .fillMaxSize()
            .padding(15.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            displayRightButton = false,
            leftIcon = R.drawable.arrow_back,
            title = "Add Pet",
            leftButtonOnClickAction = { navHostController.popBackStack() }
        )
        Spacer(modifier = Modifier.height(20.dp))

        PhotoSelection(onEvent, state)

        YSpacing15Dp()
        var selectedSpecies by remember { mutableStateOf(state.species) }
        SpeciesSelection(
            selectedSpecies = selectedSpecies,
            onSpeciesSelection = { species ->
                selectedSpecies = species
                onEvent(PetEvent.SetSpecie(species))
            }
        )

        YSpacing15Dp()
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.name,
            onValueChange = { onEvent(PetEvent.SetName(it)) },
            placeholder = { Text(text = "Full Name", style = customTypography.bodyLarge, color = Color(0xFFC0C0C0))},
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
            value = state.breed,
            onValueChange = { onEvent(PetEvent.SetBreed(it)) },
            placeholder = { Text(text = "Breed", style = customTypography.bodyLarge, color = Color(0xFFC0C0C0))},
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            isError = state.breedError != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFFC0C0C0),
                focusedBorderColor = PrimaryYellow
            ),
        )
        if (state.breedError != null) {
            Text(
                text = state.breedError,
                color = Color.Red
            )
        }

        YSpacing15Dp()
        var selectedGender by remember { mutableStateOf(state.gender) }
        GenderSelection(
            selectedGender = selectedGender,
            onGenderSelection = { gender ->
                selectedGender = gender
                onEvent(PetEvent.SetGender(gender))
            }
        )

        YSpacing15Dp()
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.weight,
            onValueChange = { onEvent(PetEvent.SetWeight(it)) },
            placeholder = { Text(text = "Weight", style = customTypography.bodyLarge, color = Color(0xFFC0C0C0))},
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            isError = state.weightError != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFFC0C0C0),
                focusedBorderColor = PrimaryYellow
            ),
        )
        if (state.weightError != null) {
            Text(
                text = state.weightError,
                color = Color.Red
            )
        }

        YSpacing15Dp()
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.dateOfBirth,
            onValueChange = { onEvent(PetEvent.SetDateOfBirth(it)) },
            placeholder = { Text(text = "Date of birth", style = customTypography.bodyLarge, color = Color(0xFFC0C0C0))},
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            isError = state.dateOfBirtheError != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFFC0C0C0),
                focusedBorderColor = PrimaryYellow
            ),
        )
        if (state.dateOfBirtheError != null) {
            Text(
                text = state.dateOfBirtheError,
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
                onEvent(PetEvent.ValidatePetData)

            },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryYellow)
        ) {
            Text(text = "save", style = customTypography.titleSmall, color = Color.White)
        }
    }
}

@Composable
private fun GenderSelection(
    selectedGender: Gender?,
    onGenderSelection: (Gender) -> Unit
) {
    val updatedSelectedGender = rememberUpdatedState(selectedGender)

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Gender",
            style = customTypography.titleSmall,
        )

        GenderOption(
            gender = Gender.MALE,
            isSelected = updatedSelectedGender.value == Gender.MALE,
            onGenderSelection = onGenderSelection
        )

        GenderOption(
            gender = Gender.FEMALE,
            isSelected = updatedSelectedGender.value == Gender.FEMALE,
            onGenderSelection = onGenderSelection
        )
    }
}

@Composable
fun GenderOption(
    gender: Gender,
    isSelected: Boolean,
    onGenderSelection: (Gender) -> Unit
) {
    val borderColor = if (isSelected) PrimaryYellow else Color.White
    val genderIcon = if (gender == Gender.MALE) R.drawable.gender_male else R.drawable.gender_female

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(60.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onGenderSelection(gender) },
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(genderIcon),
            contentDescription = "Gender Icon",
            tint = if (gender == Gender.MALE) Blue else Pink
        )
    }
}

@Composable
private fun SpeciesSelection(
    selectedSpecies: Species,
    onSpeciesSelection: (Species) -> Unit
) {
    val updatedSelectedSpecies = rememberUpdatedState(selectedSpecies)
    Text(
        text = "Species",
        style = customTypography.titleSmall,
        modifier = Modifier.fillMaxWidth()
    )
    YSpacing15Dp()
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SpeciesOption(
            species = Species.DOG,
            isSelected = updatedSelectedSpecies.value == Species.DOG,
            onSpeciesSelection = onSpeciesSelection
        )
        SpeciesOption(
            species = Species.CAT,
            isSelected = updatedSelectedSpecies.value == Species.CAT,
            onSpeciesSelection = onSpeciesSelection
        )
        SpeciesOption(
            species = Species.PIG,
            isSelected = updatedSelectedSpecies.value == Species.PIG,
            onSpeciesSelection = onSpeciesSelection
        )
        SpeciesOption(
            species = Species.COW,
            isSelected = updatedSelectedSpecies.value == Species.COW,
            onSpeciesSelection = onSpeciesSelection
        )
        SpeciesOption(
            species = Species.SHEEP,
            isSelected = updatedSelectedSpecies.value == Species.SHEEP,
            onSpeciesSelection = onSpeciesSelection
        )
    }
}

@Composable
private fun PhotoSelection(
    onEvent: (PetEvent) -> Unit,
    state: AddPetState
) {
    val context = LocalContext.current
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri == null) return@rememberLauncherForActivityResult
            context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            onEvent(PetEvent.SetPhoto(uri)) }
    )
    Text(
        text = "Photo",
        style = customTypography.titleSmall,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(20.dp))
    Box(
        modifier = Modifier
            .height(90.dp)
            .width(90.dp)
            .background(DefaultGrey, CircleShape)
            .clickable {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
        contentAlignment = Alignment.Center
    ) {
        if (state.photo != null) {
            AsyncImage(
                model = state.photo,
                contentDescription = "Pet photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(90.dp)
                    .width(90.dp)
                    .clip(CircleShape)
            )
        } else {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.camera_plus),
                contentDescription = "add photo",
                tint = DarkerGrey
            )
        }
    }
}


@Composable
fun YSpacing15Dp() {
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun SpeciesOption(
    species: Species,
    isSelected: Boolean,
    onSpeciesSelection: (Species) -> Unit
) {
    val speciesIcon = when(species) {
        Species.CAT -> R.drawable.species_cat
        Species.DOG -> R.drawable.species_dog
        Species.COW -> R.drawable.species_cow
        Species.PIG -> R.drawable.species_pig
        Species.SHEEP -> R.drawable.species_sheep
    }
    val borderColor = if (isSelected) PrimaryYellow else Color.White

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(60.dp)
            .width(60.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onSpeciesSelection(species) },
    ) {
        Image(
            painter = painterResource(id = speciesIcon),
            contentDescription = "",
            modifier = Modifier
                .height(43.dp)
                .width(43.dp)
        )
    }
}