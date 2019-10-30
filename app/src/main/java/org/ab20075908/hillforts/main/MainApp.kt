package org.ab20075908.hillforts.main

import android.app.Application
import org.ab20075908.hillforts.models.HillfortJSONStore
import org.ab20075908.hillforts.models.HillfortStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortJSONStore(applicationContext)
        info("Hillforts started")
    }
}