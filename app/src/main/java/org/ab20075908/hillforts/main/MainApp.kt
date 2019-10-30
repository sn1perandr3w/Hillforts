package org.ab20075908.hillforts.main

import android.app.Application
import org.ab20075908.hillforts.models.HillfortMemStore
import org.ab20075908.hillforts.models.HillfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    //val hillforts = ArrayList<HillfortModel>()
    val hillforts = HillfortMemStore()
    override fun onCreate() {
        super.onCreate()
        info("Hillfort started")
        // placemarks.add(PlacemarkModel("One", "About one..."))
        // placemarks.add(PlacemarkModel("Two", "About two..."))
        // placemarks.add(PlacemarkModel("Three", "About three..."))
    }
}