package com.auf.breweryapplication

import android.app.Application
import io.realm.Realm

class BreweryApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}