package com.thehexa.rickandmortyapidemo.model

data class Location(
    val created: String,
    val id: Int,
    val type: String,
    val name: String,
    val dimension: String,
    val residents: List<String>
)