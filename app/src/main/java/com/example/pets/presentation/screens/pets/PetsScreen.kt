package com.example.pets.presentation.screens.pets

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.pets.R
import com.example.pets.domain.enums.Gender
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
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetsScreen (
    navController: NavController,
    pets: List<Pet>
) {
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
                    rightIcon = R.drawable.notification,
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
                    value = "",
                    onValueChange = {},
                    placeholder = { Text(text = "Search...", style = customTypography.bodyMedium)},
                    trailingIcon = { Icon(imageVector = ImageVector.vectorResource(R.drawable.search), contentDescription = "search icon")}
                )

                LazyColumn(
                    Modifier.fillMaxSize()
                ) {
                    items(pets.size) { i ->
                        Spacer(modifier = Modifier.height(20.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SecondaryYellow, RoundedCornerShape(16.dp))
                                .height(100.dp)
                        ) {

                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                            ){
                                AsyncImage(
                                    model = pets[i].photo,
                                    error = painterResource(id = R.drawable.image),
                                    contentDescription = "pet_photo",
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(100.dp)
                                        .fillMaxSize()
                                )

                                Column(
                                    Modifier.padding(start = 10.dp, end = 30.dp),
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
                                        when(pets[i].specie) {
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


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun preview() {
    val pet = Pet(
        "Piruças",
        Species.CAT,
        Gender.FEMALE,
        LocalDate.now(),
        "Gato do mato",
        Uri.parse("https://live.staticflickr.com/5800/31456463045_5a0af4ddc8_s.jpg"),
        12.1f,
        emptyList(),
        emptyList()
    )

    PetsScreen(rememberNavController(), listOf(pet))
}