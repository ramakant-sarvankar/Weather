package com.crepuscular.weather.repository.model

import com.google.gson.annotations.SerializedName


data class WeatherData (

  @SerializedName("weather"    ) var weather    : ArrayList<Weather> = arrayListOf(),
  @SerializedName("base"       ) var base       : String?            = null,
  @SerializedName("main"       ) var main       : Main?              = Main(),
  @SerializedName("visibility" ) var visibility : Int?               = null,
  @SerializedName("wind"       ) var wind       : Wind?              = Wind(),
  @SerializedName("clouds"     ) var clouds     : Clouds?            = Clouds(),
  @SerializedName("dt"         ) var dt         : Int?               = null,
  @SerializedName("timezone"   ) var timezone   : Int?               = null,
  @SerializedName("id"         ) var id         : Int?               = null,
  @SerializedName("name"       ) var name       : String?            = null,
  @SerializedName("cod"        ) var cod        : Int?               = null

)