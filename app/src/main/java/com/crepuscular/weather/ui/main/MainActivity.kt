package com.crepuscular.weather.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.crepuscular.weather.R
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.api_key))
        }

        if (savedInstanceState == null) {
            val au = AutocompleteSupportFragment.newInstance().setPlaceFields(
                Arrays.asList(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.LAT_LNG
                )
            )
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, au)
                .commitNow()

            au.setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    lifecycleScope.launch(Dispatchers.Main) { au.setText("") }
                    val intent = Intent(applicationContext, MapsActivity::class.java)
                    intent.putExtra("LAT_LONG", place.latLng)
                    intent.putExtra("name", place.name)
                    startActivity(intent)
                }

                override fun onError(status: Status) {
                    Log.i("TAG", "An error occurred: $status")
                }
            })
        }
    }
}