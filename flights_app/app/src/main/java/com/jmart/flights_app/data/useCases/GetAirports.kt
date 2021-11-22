package com.jmart.flights_app.data.useCases

import com.jmart.flights_app.data.dataSource.repositories.AirportRepository

class GetAirPorts(private val airportRepository: AirportRepository) {
    suspend operator fun invoke() = airportRepository.getAirports()
}