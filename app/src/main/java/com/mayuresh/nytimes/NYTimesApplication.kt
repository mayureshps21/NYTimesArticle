package com.mayuresh.nytimes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NYTimesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
