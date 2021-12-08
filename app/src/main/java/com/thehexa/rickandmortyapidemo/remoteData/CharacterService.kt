package com.thehexa.rickandmortyapidemo.remoteData

import com.thehexa.rickandmortyapidemo.model.Character
import com.thehexa.rickandmortyapidemo.model.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<Character>

    @GET("character")
    suspend fun getAllCharacters() : Response<CharacterList>
}