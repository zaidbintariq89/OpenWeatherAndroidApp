package com.framework.openweatherandroidapp.repository.db


class RoomConfig {
    companion object {

        const val DATABASE_WEATHER = "weather.db"
        const val TABLE_CITIES= "cities"

        private const val SELECT_FROM = "SELECT * FROM "

        const val SELECT_CITIES = SELECT_FROM + TABLE_CITIES
    }
}