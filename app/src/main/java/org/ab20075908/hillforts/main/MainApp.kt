package org.ab20075908.hillforts.main

import android.app.Application
import org.ab20075908.hillforts.models.HillfortStore
import org.ab20075908.hillforts.models.json.HillfortJSONStore
import org.ab20075908.hillforts.models.UserModel
import org.ab20075908.hillforts.models.firebase.HillfortFireStore
import org.ab20075908.hillforts.room.HillfortStoreRoom
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

//Created by Andrew Bates. Adapted from Hillfort example provided by Eamonn de Leastar


class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortFireStore

    override fun onCreate() {
        super.onCreate()
        //hillforts = HillfortJSONStore(applicationContext)
        //hillforts = HillfortStoreRoom(applicationContext)
        hillforts = HillfortFireStore(applicationContext)
        info("Hillforts started")
    }
}