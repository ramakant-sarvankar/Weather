package com.crepuscular.weather.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.crepuscular.weather.R
import com.crepuscular.weather.repository.model.WeatherData
import com.crepuscular.weather.ui.main.viewmodel.MapActivityViewModel
import com.crepuscular.weather.ui.main.viewmodel.MapActivityViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var latLng: LatLng
    private lateinit var cityName: String
    private lateinit var weatherData: WeatherData
    private var mapActivityViewModel: MapActivityViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_maps)

        latLng = intent.getParcelableExtra<LatLng>("LAT_LONG")!!
        cityName = intent.getStringExtra("name")!!
        supportActionBar?.title = cityName
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapActivityViewModel = ViewModelProvider(
            this,
            MapActivityViewModelFactory(application)
        )[MapActivityViewModel::class.java]

        mapActivityViewModel!!.getWeatherUpdates(latLng).observe(this, Observer {
            Log.i("TAG", "Weather: " + it.toString())
            weatherData = it
            updateData()
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun updateData() {
        mMap.setInfoWindowAdapter(CustomInfoWindowForGoogleMap(this))
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(cityName)
                .snippet(getSnippet())
        )
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(12f)
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun getSnippet(): String {
        val str =
            "Temperature : " + weatherData.main?.temp + " °C\n" +
                    weatherData.weather[0].main + " : " + weatherData.weather[0].description + "\n" +
                    "Wind Speed : " + weatherData.wind?.speed + " m/s\n" +
                    "Humidity : " + weatherData.main?.humidity + "%"
        return str
    }
}
