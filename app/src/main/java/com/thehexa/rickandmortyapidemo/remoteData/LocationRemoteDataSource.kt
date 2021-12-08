package com.thehexa.rickandmortyapidemo.remoteData

import com.thehexa.rickandmortyapidemo.model.Location
import com.thehexa.rickandmortyapidemo.utils.Resource
import javax.inject.Inject

class LocationRemoteDataSource @Inject constructor(
    private val locationService: LocationService
){
    suspend fun getLocation(id: Int): Resource<Location>{
        try {
            val response = locationService.getLocation(id)
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