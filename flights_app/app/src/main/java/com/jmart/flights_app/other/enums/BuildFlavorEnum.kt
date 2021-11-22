package com.jmart.flights_app.other.enums

import android.annotation.SuppressLint
import java.util.*

enum class BuildFlavorEnum {
    TST,
    ACCP,
    PROD;

    @SuppressLint("DefaultLocale")
    override fun toString(): String {
        return super.toString().lowercase(Locale.getDefault())
    }
}