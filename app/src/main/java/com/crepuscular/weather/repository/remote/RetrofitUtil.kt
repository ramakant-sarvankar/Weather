package com.crepuscular.weather.repository.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUtil {
    companion object {
        private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private val OPEN_WEATHER_API_KEY = ""

        private val client = OkHttpClient.Builder()
            .addInterceptor(Interceptor {
                var original = it.request()
                val url = original.url().newBuilder().addQueryParameter("appid", OPEN_WEATHER_API_KEY).build()
                original = original.newBuilder().url(url).build()
                it.proceed(original)
            }).build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()

        val apiService = retrofit.create(APIService::class.java)
    }
}