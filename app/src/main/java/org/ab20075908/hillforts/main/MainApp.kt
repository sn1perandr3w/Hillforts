package org.ab20075908.hillforts.main

import android.app.Application
import org.ab20075908.hillforts.models.HillfortJSONStore
import org.ab20075908.hillforts.models.HillfortStore
import org.ab20075908.hillforts.models.UserModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

//Created by Andrew Bates. Adapted from Placemark example provided by Eamonn de Leastar


class MainApp : Application(), AnkoLogger {

    //Provides ability to manipulate and access our JSON files
    lateinit var hillforts: HillfortJSONStore

    //stores signed in user
    lateinit var signedInUser: UserModel

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortJSONStore(applicationContext)
        info("Hillforts started")
    }
}