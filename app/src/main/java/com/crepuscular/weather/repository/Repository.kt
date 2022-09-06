package com.crepuscular.weather.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crepuscular.weather.repository.model.WeatherData
import com.crepuscular.weather.repository.remote.APIService
import com.crepuscular.weather.repository.remote.ApiResponse
import com.crepuscular.weather.repository.remote.RemoteRepository
import com.crepuscular.weather.repository.remote.RetrofitUtil
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class Repository(
    private val context: Context,
    private val compositeDisposable: CompositeDisposable
) {
    private val imageResponse = MutableLiveData<ApiResponse?>()
    private val apiService: APIService = RetrofitUtil.apiService
    private val remoteRepository = RemoteRepository(apiService)

    fun getWeatherData(lat: Double, lng: Double): LiveData<ApiResponse> {
        var imageData = MutableLiveData<ApiResponse>();
        val observable =
            remoteRepository.getWeatherFromRemote(lat, lng)?.toObservable()
                ?.subscribeOn(Schedulers.io())
                ?.subscribe({
                    Log.i("TAG", "getWeatherData: " + it.body())
                    if (it.isSuccessful) {
                        val response = ApiResponse(it.body(), it.message(), true);
                        imageData.postValue(response)
                    } else {
                        val response = ApiResponse(null, it.message(), false);
                        imageData.postValue(response)
                    }

                }, {
                    it.printStackTrace()
                    val response = ApiResponse(null, it.message, false);
                    imageData.postValue(response)
                })
        compositeDisposable.add(observable)
        return imageData
    }
}