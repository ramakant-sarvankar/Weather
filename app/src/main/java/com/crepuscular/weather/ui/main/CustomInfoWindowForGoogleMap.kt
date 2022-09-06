package com.crepuscular.weather.ui.main

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.crepuscular.weather.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowForGoogleMap(context: Context) : GoogleMap.InfoWindowAdapter {

    var mContext = context
    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.info_window, null)

    private fun rendowWindowText(marker: Marker, view: View){

        val tvTitle = view.findViewById<TextView>(R.id.title)
        val tvTemp = view.findViewById<TextView>(R.id.snipet)
//        val tvWeather = view.findViewById<TextView>(R.id.txtWeather)
//        val tvWindSpeed = view.findViewById<TextView>(R.id.txtWindSpeed)
//        val tvHumidity = view.findViewById<TextView>(R.id.txtHumidity)

        tvTitle.text = marker.title
        tvTemp.text = marker.snippet

    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}