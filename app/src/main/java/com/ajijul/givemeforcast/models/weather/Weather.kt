package com.ajijul.givemeforcast.models.weather

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)