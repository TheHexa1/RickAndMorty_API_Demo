package com.thehexa.rickandmortyapidemo.remoteData

import com.thehexa.rickandmortyapidemo.model.Location
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationService {
    @GET("location/{id}")
    suspend fun getLocation(@Path("id") id: Int): Response<Location>
}