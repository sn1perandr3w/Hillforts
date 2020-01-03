package org.ab20075908.hillforts.models

interface HillfortStore {

    //Hillfort functions
    fun findAll(): List<HillfortModel>
    fun create(hillfort: HillfortModel)
    fun update(hillfort: HillfortModel)
    fun delete(hillfort: HillfortModel)
    fun findById(id:Long) : HillfortModel?
    fun clear()


}