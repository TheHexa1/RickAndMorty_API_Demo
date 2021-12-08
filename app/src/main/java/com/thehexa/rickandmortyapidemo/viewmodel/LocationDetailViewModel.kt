package com.thehexa.rickandmortyapidemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.thehexa.rickandmortyapidemo.model.Location
import com.thehexa.rickandmortyapidemo.repository.LocationRepository
import com.thehexa.rickandmortyapidemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _location = _id.switchMap { id ->
        repository.getLocation(id)
    }
    val location: LiveData<Resource<Location>> = _location

    fun start(id: Int) {
        _id.value = id
    }

}
