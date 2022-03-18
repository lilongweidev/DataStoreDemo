package com.llw.datastore

import android.app.Application

/**
 * App
 * @author llw
 */
class App : Application() {

    companion object {
        lateinit var instance : App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
