package com.jmart.flights_app.data.useCases

import com.jmart.flights_app.data.dataSource.storageHandler.PreferenceStorageHandler

class SetUserDistanceUnit(private val preferenceStorageHandler: PreferenceStorageHandler) {
    suspend operator fun invoke(distanceUnit: String) = preferenceStorageHandler.setUserDistanceUnit(distanceUnit)
}