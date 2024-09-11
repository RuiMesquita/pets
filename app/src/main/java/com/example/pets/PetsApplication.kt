package com.example.pets

import android.app.Application
import com.example.pets.domain.repository.EventRepository
import com.example.pets.domain.repository.PetsRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class PetsApplication: Application() {

    @Inject
    lateinit var repository: EventRepository

    override fun onCreate() {
        super.onCreate()

        // delete overdue events
        CoroutineScope(Dispatchers.IO).launch {
            repository.getOverdueEvents()
                .collect {
                    repository.deleteEvents(it)
                }
        }
    }
}