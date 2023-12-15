package com.example.pets.presentation.screens.pets

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pets.data.entities.toPet
import com.example.pets.domain.model.Pet
import com.example.pets.domain.repository.PetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class PetsViewModel @Inject constructor(
    private val repository: PetsRepository
): ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> get() = _searchText.asStateFlow()

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets = searchText
        .debounce(500)
        .combine(_pets) { text, pets ->
            if (text.isBlank()) {
                pets
            }
            else {
                pets.filter {
                    it.doesMatchSearchCriteria(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _pets.value
        )

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

    fun onSearchTextChange(text: String) {
        _searchText.value = text

    }
}