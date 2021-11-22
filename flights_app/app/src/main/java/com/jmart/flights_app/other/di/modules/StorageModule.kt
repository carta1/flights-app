package com.jmart.flights_app.other.di.modules

import com.jmart.flights_app.data.dataSource.storageHandler.PreferenceStorageHandler
import com.jmart.flights_app.other.preferences.AppPrefsStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    abstract fun providesPreferenceStorage(
        appPreferenceStorage: AppPrefsStorage
    ): PreferenceStorageHandler
}