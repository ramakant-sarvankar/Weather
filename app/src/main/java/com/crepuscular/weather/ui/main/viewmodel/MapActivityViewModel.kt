package com.crepuscular.weather.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crepuscular.weather.repository.Repository
import com.crepuscular.weather.repository.model.WeatherData
import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class MapActivityViewModel(application: Application) : AndroidViewModel(application) {
    val compositeDisposable = CompositeDisposable()
    private val weatherData = MutableLiveData<WeatherData>()
    val repository = Repository(application, compositeDisposable)
    fun getWeatherUpdates(latLng : LatLng) : LiveData<WeatherData> {
        viewModelScope.launch {
            repository.getWeatherData(latLng.latitude, latLng.longitude).observeForever() {
                if(it.isSuccess) {
                    weatherData.setValue(it.weatherData!!)
                }else{

                }
            }
        }
        return weatherData
    }
}