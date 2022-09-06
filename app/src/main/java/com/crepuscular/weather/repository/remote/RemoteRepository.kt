package com.crepuscular.weather.repository.remote;

import android.content.Context;
import com.crepuscular.weather.repository.model.WeatherData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class RemoteRepository(
    private val apiService: APIService) {
    fun getWeatherFromRemote(lat : Double, lng : Double): Single<Response<WeatherData>>? =
        apiService?.getWeather(lat, lng)
            ?.subscribeOn(Schedulers.io())

}
