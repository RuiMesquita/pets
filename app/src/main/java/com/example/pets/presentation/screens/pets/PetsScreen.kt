package com.example.pets.presentation.screens.pets

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pets.R
import com.example.pets.domain.enums.Species
import com.example.pets.domain.model.Pet
import com.example.pets.presentation.navigation.Screens
import com.example.pets.presentation.screens.common.TopBar
import com.example.pets.presentation.ui.theme.DarkerGrey
import com.example.pets.presentation.ui.theme.DefaultGrey
import com.example.pets.presentation.ui.theme.PetsTheme
import com.example.pets.presentation.ui.theme.PrimaryYellow
import com.example.pets.presentation.ui.theme.SecondaryYellow
import com.example.pets.presentation.ui.theme.WashedWhite
import com.example.pets.presentation.ui.theme.customTypography

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetsScreen (
    navController: NavController,
    viewModel: PetsViewModel
) {
    val pets by viewModel.pets.collectAsState()
    val searchText by viewModel.searchText.collectAsState()

    PetsTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(Screens.AddPet.route) },
                    shape = CircleShape,
                    contentColor = Color.White,
                    containerColor = PrimaryYellow,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add pet"
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WashedWhite)
                    .padding(15.dp)
            ) {

                TopBar(
                    rightIcon = R.drawable.calendar_blank,
                    rightButtonOnClickAction = {navController.navigate(Screens.Calendar.route)},
                    displayLeftButton = false,
                    title = "Pets",
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = DefaultGrey,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    value = searchText,
                    onValueChange = viewModel::onSearchTextChange,
                    placeholder = { Text(text = "Search...", style = customTypography.bodyMedium)},
                    trailingIcon = { Icon(imageVector = ImageVector.vectorResource(R.drawable.search), contentDescription = "search icon")}
                )

                if (pets.isNotEmpty()) {
                    LazyColumn(
                        Modifier.fillMaxSize()
                    ) {
                        items(pets.size) { i ->
                            PetItem(
                                pets = pets,
                                i = i,
                                onClick = { navController.navigate(Screens.PetProfile.route + "/${pets[i].id}") }
                            )
                        }
                    }
                }
                else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                        .fillMaxSize(),
                    ) {
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.no_pets_found),
                                contentDescription = "empty state screen",
                                modifier = Modifier
                                    .size(200.dp)
                            )
                            Text(
                                text = "No Pets Found",
                                style = customTypography.titleLarge
                            )
                        }

                    }
                }

            }
        }
    }
}


@Composable
private fun PetItem(
    pets: List<Pet>,
    i: Int,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(20.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SecondaryYellow, RoundedCornerShape(16.dp))
            .height(100.dp)
            .border(1.dp, PrimaryYellow, RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = pets[i].photo,
                error = painterResource(id = R.drawable.image),
                contentDescription = "pet_photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
            )

            Column(
                Modifier
                    .weight(2f)
                    .padding(start = 10.dp, end = 30.dp),
            ) {
                Text(
                    text = pets[i].name,
                    style = customTypography.titleSmall,
                    color = DarkerGrey
                )
                Divider(
                    color = DarkerGrey
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    when (pets[i].specie) {
                        Species.CAT -> DisplayPetIcon(R.drawable.species_cat)
                        Species.DOG -> DisplayPetIcon(R.drawable.species_dog)
                        Species.COW -> DisplayPetIcon(R.drawable.species_cow)
                        Species.PIG -> DisplayPetIcon(R.drawable.species_pig)
                        Species.SHEEP -> DisplayPetIcon(R.drawable.species_sheep)
                    }

                    Column {
                        Text(
                            text = pets[i].specie.toString(),
                            style = customTypography.bodyMedium,
                            color = DarkerGrey
                        )
                        Text(
                            text = pets[i].dateOfBirth.toString(),
                            style = customTypography.bodyMedium,
                            color = DarkerGrey
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun DisplayPetIcon(image: Int) {
    Image(
        painter = painterResource(id = image),
        contentDescription = "icon",
        modifier = Modifier
            .padding(5.dp)
            .height(35.dp)
            .width(35.dp)
    )
}
