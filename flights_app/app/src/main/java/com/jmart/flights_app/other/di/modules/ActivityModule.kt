package com.jmart.flights_app.other.di.modules

import android.app.Activity
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

object ActivityModule {
    @Module
    @InstallIn(ActivityComponent::class)
    object ActivityModule {

        @Provides
        fun appComponentActivity(activity: Activity): ComponentActivity = activity as  ComponentActivity
    }

}