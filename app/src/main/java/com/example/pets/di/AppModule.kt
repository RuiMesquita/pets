package com.example.pets.di

import android.app.Application
import androidx.room.Room
import com.example.pets.data.data_source.PetsDatabase
import com.example.pets.data.repository.PetsRepositoryImpl
import com.example.pets.domain.repository.PetsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): PetsDatabase {
        return Room.databaseBuilder(app, PetsDatabase::class.java, "pets_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePetsRepository(db: PetsDatabase): PetsRepository {
        return PetsRepositoryImpl(db.petsDao())
    }
}