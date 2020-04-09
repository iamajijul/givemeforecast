package com.ajijul.givemeforcast.models.forecast

data class ThreeHoursModel(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val rain: Rain,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
)