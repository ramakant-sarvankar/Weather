package com.crepuscular.weather.repository.remote

import com.crepuscular.weather.repository.model.WeatherData

data class ApiResponse(
    val weatherData: WeatherData? = null,
    val errorMessage : String? = null,
    val isSuccess: Boolean
)
