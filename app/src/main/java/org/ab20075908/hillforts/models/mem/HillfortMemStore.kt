/*
//UNUSED


package org.ab20075908.hillforts.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HillfortMemStore : HillfortStore, AnkoLogger {
    override fun updateCredentials(userSignedIn: UserModel, updatedUser: UserModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login(email: String, password: String): UserModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createUser(email: String, password: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val hillforts = ArrayList<HillfortModel>()

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun create(hillfort: HillfortModel) {
        hillfort.id = getId()
        hillforts.add(hillfort)
        logAll()
    }

    override fun update(hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
        if (foundHillfort != null) {
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.image1 = hillfort.image1
            foundHillfort.image2 = hillfort.image2
            foundHillfort.image3 = hillfort.image3
            foundHillfort.image4 = hillfort.image4
            foundHillfort.lat = hillfort.lat
            foundHillfort.lng = hillfort.lng
            foundHillfort.zoom = hillfort.zoom

            logAll();
        }
    }

    override fun delete(hillfort: HillfortModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun logAll() {
        hillforts.forEach { info("${it}") }
    }

    override fun findById(id:Long) : HillfortModel? {
        val foundHillfort: HillfortModel? = hillforts.find { it.id == id }
        return foundHillfort
    }

     fun clear()
}

 */