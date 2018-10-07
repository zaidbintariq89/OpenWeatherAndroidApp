package com.framework.openweatherandroidapp.utils

import com.framework.openweatherandroidapp.R
import java.util.*

class WeatherUtils private constructor() {

    enum class TEMP_UNIT {
        Celsius, Fahrenheit, Kelvin
    }

    enum class WIND_UNIT {
        MS, KMH, MPH
    }

    companion object {

        fun getTempString(kelvinTemp: Double, tempUnit: TEMP_UNIT): String {

            return when (tempUnit) {
                TEMP_UNIT.Celsius -> {
                    val celsiusTemp = convertToCelsius(kelvinTemp)
                    String.format(Locale.ENGLISH, "%.1f °C", celsiusTemp)
                }
                TEMP_UNIT.Fahrenheit -> {
                    val fahrenheitTemp = convertToFahrenheit(kelvinTemp)
                    String.format(Locale.ENGLISH, "%.1f °F", fahrenheitTemp)
                }
                TEMP_UNIT.Kelvin -> String.format(Locale.ENGLISH, "%.1f K", kelvinTemp)
            }
        }

        private fun convertToCelsius(kelvinTemp: Double): Double {
            return kelvinTemp - 273.15
        }

        private fun convertToFahrenheit(kelvinTemp: Double): Double {
            return kelvinTemp * 9 / 5.0 - 459.67
        }

        private fun convertToKmh(msWindSpeed: Double): Double {
            return msWindSpeed * 3.6
        }

        private fun convertToMph(msWindSpeed: Double): Double {
            return msWindSpeed * 2.2369
        }

//    fun getCoordinatesString(latLng: LatLng): String {
//        return String.format(Locale.ENGLISH, "(%.2f, %.2f)", latLng.latitude, latLng.longitude)
//    }

        fun getHumidityString(humidity: Double): String {
            return String.format(Locale.ENGLISH, "Humidity:  %.1f %%", humidity)
        }

        fun getWindString(msWindSpeed: Double, windUnit: WIND_UNIT): String {

            return when (windUnit) {
                WIND_UNIT.MS -> String.format(Locale.ENGLISH, "Wind:  %.1f m/s", msWindSpeed)
                WIND_UNIT.KMH -> {
                    val kmhWindSpeed = convertToKmh(msWindSpeed)
                    String.format(Locale.ENGLISH, "Wind:  %.1f km/h", kmhWindSpeed)
                }
                WIND_UNIT.MPH -> {
                    val mphWindSpeed = convertToMph(msWindSpeed)
                    String.format(Locale.ENGLISH, "Wind:  %.1f mph", mphWindSpeed)
                }
            }
        }

        fun getExtremeTempsString(maxTemp: Double, minTemp: Double, tempUnit: TEMP_UNIT): String {
            val maxTempString = getTempString(maxTemp, tempUnit)
            val minTempString = getTempString(minTemp, tempUnit)
            return String.format(Locale.ENGLISH, "Min:  %s  |  Max:  %s", minTempString, maxTempString)
        }

        fun getWeatherImageId(weatherId: Int): Int {
            return if (weatherId == 800 || weatherId == 904) {
                R.drawable.ic_clear
            } else if (weatherId == 801) {
                R.drawable.ic_few_clouds
            } else if (weatherId == 802) {
                R.drawable.ic_scattered_clouds
            } else if (weatherId == 803 || weatherId == 804) {
                R.drawable.ic_broken_clouds
            } else if (weatherId in 200..299) {
                R.drawable.ic_thunderstorm
            } else if (weatherId == 906 || weatherId in 300..399 || weatherId in 520..599) {
                R.drawable.ic_shower_rain
            } else if (weatherId == 511 || weatherId == 903 || weatherId in 600..699) {
                R.drawable.ic_snow
            } else if (weatherId in 500..509) {
                R.drawable.ic_rain
            } else if (weatherId in 701..779) {
                R.drawable.ic_mist
            } else if (weatherId in 951..959) {
                R.drawable.ic_windy
            } else {
                R.drawable.ic_hurricane
            }
        }
    }
}