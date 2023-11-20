package com.example.pets.presentation.screens.pets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.data.entities.toPet
import com.example.pets.domain.model.Pet
import com.example.pets.domain.repository.PetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class PetsViewModel @Inject constructor(
    private val repository: PetsRepository
): ViewModel() {

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> get() = _pets.asStateFlow()

    init {
        getPets()
    }

    private fun getPets() {
        viewModelScope.launch {
            repository.getPets()
                .flowOn(Dispatchers.IO)
                .collect { pet ->
                    val petsList = pet.map { petEntity -> petEntity.toPet() }
                    _pets.value = petsList
                }
        }
    }
}