package com.thehexa.rickandmortyapidemo.repository

import com.thehexa.rickandmortyapidemo.remoteData.LocationRemoteDataSource
import com.thehexa.rickandmortyapidemo.utils.performLocationGetOperation
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val remoteDataSource: LocationRemoteDataSource
) {
    fun getLocation(id: Int) = performLocationGetOperation(
        networkCall = { remoteDataSource.getLocation(id) }
    )
}