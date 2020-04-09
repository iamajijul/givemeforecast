package com.ajijul.givemeforcast.models.forecast

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String
)