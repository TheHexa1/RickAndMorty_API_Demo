package com.thehexa.rickandmortyapidemo.di

import android.content.Context
import com.thehexa.rickandmortyapidemo.db.AppDatabase
import com.thehexa.rickandmortyapidemo.db.CharacterDao
import com.thehexa.rickandmortyapidemo.remoteData.CharacterRemoteDataSource
import com.thehexa.rickandmortyapidemo.remoteData.CharacterService
import com.thehexa.rickandmortyapidemo.remoteData.LocationRemoteDataSource
import com.thehexa.rickandmortyapidemo.remoteData.LocationService
import com.thehexa.rickandmortyapidemo.repository.CharacterRepository
import com.thehexa.rickandmortyapidemo.repository.LocationRepository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharacterService = retrofit.create(CharacterService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(characterService: CharacterService) = CharacterRemoteDataSource(characterService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.characterDao()

    @Singleton
    @Provides
    fun provideCharacterRepository(remoteDataSource: CharacterRemoteDataSource,
                          localDataSource: CharacterDao) =
        CharacterRepository(remoteDataSource, localDataSource)

    @Provides
    fun provideLocationService(retrofit: Retrofit): LocationService = retrofit.create(LocationService::class.java)

    @Singleton
    @Provides
    fun provideLocationRemoteDataSource(locationService: LocationService) = LocationRemoteDataSource(locationService)

    @Singleton
    @Provides
    fun provideLocationRepository(remoteDataSource: LocationRemoteDataSource) =
        LocationRepository(remoteDataSource)
}