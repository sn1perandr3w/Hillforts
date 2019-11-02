package org.ab20075908.hillforts.models

interface HillfortStore {
    fun findAll(): List<HillfortModel>
    fun create(hillfort: HillfortModel)
    fun update(hillfort: HillfortModel)
    fun delete(hillfort: HillfortModel)


    fun createUser(email : String, password : String)
    fun login(email : String, password : String) : UserModel?
}