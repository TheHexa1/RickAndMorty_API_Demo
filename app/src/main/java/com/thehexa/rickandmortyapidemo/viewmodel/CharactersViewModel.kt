package com.thehexa.rickandmortyapidemo.viewmodel

import androidx.lifecycle.ViewModel
import com.thehexa.rickandmortyapidemo.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    repository: CharacterRepository
) : ViewModel() {
    val characters = repository.getCharacters()
}
