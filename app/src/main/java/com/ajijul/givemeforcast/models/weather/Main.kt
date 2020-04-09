package com.ajijul.givemeforcast.models.weather

import com.ajijul.givemeforcast.utils.toCelsius

data class Main(
    val humidity: Double,
    val pressure: Double,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
){
    val _temp_min: String
        get() = temp_min.toCelsius()


    val _temp_max: String
        get() = temp_max.toCelsius()
}