package org.ab20075908.hillforts.models

interface HillfortStore {

    //Hillfort functions
    fun findAll(): List<HillfortModel>
    fun create(hillfort: HillfortModel)
    fun update(hillfort: HillfortModel)
    fun delete(hillfort: HillfortModel)

    //User functions
    
    fun createUser(email : String, password : String)
    fun login(email : String, password : String) : UserModel?
    fun updateCredentials(userSignedIn: UserModel, updatedUser : UserModel)

}