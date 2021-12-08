package com.thehexa.rickandmortyapidemo.remoteData

import com.thehexa.rickandmortyapidemo.model.Character
import com.thehexa.rickandmortyapidemo.model.CharacterList
import com.thehexa.rickandmortyapidemo.utils.Resource
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val characterService: CharacterService
){
    suspend fun getCharacters(): Resource<CharacterList>{
        try {
            val response = characterService.getAllCharacters()//call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return emitError(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return emitError(e.message ?: e.toString())
        }
    }

    suspend fun getCharacter(id: Int): Resource<Character>{
        try {
            val response = characterService.getCharacter(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return emitError(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return emitError(e.message ?: e.toString())
        }
    }

    private fun <T> emitError(message: String): Resource<T> {
        return Resource.error("Network call has failed : $message")
    }
}