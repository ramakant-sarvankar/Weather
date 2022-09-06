package com.crepuscular.weather.repository.remote

import com.crepuscular.weather.repository.model.WeatherData
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("weather?units=metric")
    fun getWeather(@Query("lat") lat : Double, @Query("lon") lng : Double) : Single<Response<WeatherData>>
}