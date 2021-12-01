package com.jmart.flights_app.data.useCases

import com.jmart.flights_app.data.dataSource.storageHandler.PreferenceStorageHandler

class GetUserDistanceUnit(private val preferenceStorageHandler: PreferenceStorageHandler) {
    operator fun invoke() = preferenceStorageHandler.getUserDistanceUnit
}