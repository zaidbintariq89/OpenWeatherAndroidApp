package com.framework.openweatherandroidapp

import com.framework.openweatherandroidapp.model.*
import com.framework.openweatherandroidapp.repository.WeatherRepository
import com.framework.openweatherandroidapp.repository.WeatherRepositoryImpl
import com.framework.openweatherandroidapp.repository.api.ApiService
import com.framework.openweatherandroidapp.repository.db.CityEntity
import com.framework.openweatherandroidapp.repository.db.RoomDataSource
import com.framework.openweatherandroidapp.repository.db.WeatherCitiesDao
import io.reactivex.Flowable
import io.reactivex.Single
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryTest {

    @Mock
    private lateinit var roomDataSource: RoomDataSource

    @Mock
    private lateinit var remoteWeatherService: ApiService

    @Mock
    private lateinit var weatherSearchCityDao: WeatherCitiesDao

    private lateinit var weatherRepository: WeatherRepository

    @Captor
    private lateinit var cityEntityArgumentCaptor: ArgumentCaptor<CityEntity>

    @Captor
    private lateinit var cityQueryArgumentCaptor: ArgumentCaptor<String>

    @Captor
    private lateinit var apiKeyArgumentCaptor: ArgumentCaptor<String>

    @Captor
    private lateinit var metricArgumentCaptor: ArgumentCaptor<String>


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        weatherRepository = WeatherRepositoryImpl(remoteWeatherService, roomDataSource)
        Mockito.`when`(roomDataSource.weatherSearchCityDao()).thenReturn(weatherSearchCityDao)
    }

    @Test
    fun testNoCitiesReturned_whenNoCitiesSaved() {
        // Given that the RoomDataSource returns an empty list of cities
        Mockito.`when`(roomDataSource.weatherSearchCityDao().getAllCities()).thenReturn(Flowable.empty<List<CityEntity>>())

        //When getting the active shopping lists
        weatherRepository.getCities()
                .test()
                .assertNoValues()
    }

    @Test
    fun testCitiesReturned_whenCitiesSaved() {
        Mockito.`when`(roomDataSource.weatherSearchCityDao().getAllCities()).thenReturn(Flowable.just(listOf(CityEntity(cityName = "Lahore", icon = 800, summary = "Clear Day"), CityEntity(cityName = "Singapore", icon = 801, summary = "Few Clouds"))))


        weatherRepository.getCities()
                .test()
                .assertValue { citiesList: List<CityEntity> ->
                    citiesList.size == 2
                            && citiesList[0].cityName.equals("Lahore")
                            && citiesList[1].cityName.equals("Singapore")
                }
    }

    @Test
    fun testAddCity_insertsCityEntityToDatabase() {
        val insertedCityName = "Lahore"

        weatherRepository.addCity(insertedCityName)

        //Room is currently not supporting Completable, so there is no other way to make addCity become Completable and use awaitTerminalEvent
        Thread.sleep(1000)

        Mockito.verify<WeatherCitiesDao>(roomDataSource.weatherSearchCityDao()).insertCity(capture(cityEntityArgumentCaptor))

        Assert.assertEquals(insertedCityName, cityEntityArgumentCaptor.value.cityName)
    }

    @Test
    fun testWeatherDetailsDTOReturned_whenRequested() {
        val weather = listOf(weatherItem)
        val weatherResponse = WeatherModel(dt, coord, visibility, weather, name, cod, main, clouds, id, sys, base, wind)

        val searchedCity = "Islamabad"
        val appKey = "b77b89e217816136ecd0cc5c8fc23fca"
        Mockito.`when`(remoteWeatherService.getCurrentWeather(searchedCity, appKey, "metric")).thenReturn(Single.just(weatherResponse))

        weatherRepository.getWeather(searchedCity,appKey).test()
                .assertNoErrors()
                .assertValue { weatherDeatailsDTO: WeatherModel ->
                    weatherDeatailsDTO.name == searchedCity
                }

        Mockito.verify<ApiService>(remoteWeatherService).getCurrentWeather(capture(cityQueryArgumentCaptor), capture(apiKeyArgumentCaptor), capture(metricArgumentCaptor))

        Assert.assertEquals(weatherResponse.name, cityQueryArgumentCaptor.value)
    }

    //MOCKS FOR REMOTE API RETURNED OBJECTS
    companion object {
        val base = "stations"
        val visibility = 7000
        val dt = 1539016200
        val id = 1176615
        val name = "Islamabad"
        val cod = 200
        val coord = Coord(73.06, 33.69)
        val weatherItem = WeatherItem("201", "Thunderstorm", "thunderstorm with rain", 11)
        val main = Main(292.15,
                1011.0,
                72.0,
                292.15,
                292.15)
        val wind = Wind(12.3, 310.0)
        val clouds = Clouds(75)
        val sys = Sys(1,
                7146,
                0.0082,
                "PK",
                1538960848,
                1539002543)
    }
}