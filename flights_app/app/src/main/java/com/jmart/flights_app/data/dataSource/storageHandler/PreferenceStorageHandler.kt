package com.jmart.flights_app.data.dataSource.storageHandler

import kotlinx.coroutines.flow.Flow

interface PreferenceStorageHandler {
    val getUserDistanceUnit: Flow<String>
    suspend fun setUserDistanceUnit(distanceUnit: String)
}