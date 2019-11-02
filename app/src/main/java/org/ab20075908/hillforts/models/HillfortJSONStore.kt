package org.ab20075908.hillforts.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.ab20075908.hillforts.helpers.exists
import org.ab20075908.hillforts.helpers.read
import org.ab20075908.hillforts.helpers.write
import org.jetbrains.anko.AnkoLogger
import org.ab20075908.hillforts.helpers.*
import org.jetbrains.anko.info
import java.util.*

val JSON_FILE = "hillforts.json"
val USRJSON_FILE = "users.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<HillfortModel>>() {}.type
val listType2 = object : TypeToken<ArrayList<UserModel>>() {}.type


fun generateRandomId(): Long {
    return Random().nextLong()
}

class HillfortJSONStore : HillfortStore, AnkoLogger {

    val context: Context
    var hillforts = mutableListOf<HillfortModel>()
    var users = mutableListOf<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            info("DESERIALIZING")
            deserialize()
        }
        if(exists(context, USRJSON_FILE))
        {
            info("DESERIALIZING USERS")
            deserializeUsers()
        }
    }

    override fun findAll(): MutableList<HillfortModel> {
        return hillforts
    }

    override fun create(hillfort: HillfortModel) {
        hillfort.id = generateRandomId()
        hillforts.add(hillfort)
        serialize()
    }

    override fun createUser(email : String, password : String) {

        var user : UserModel  = UserModel()
        user.email = email
        user.password = password
        users.add(user)
        serializeUsers()
    }

    override fun login(email : String, password : String) : UserModel?
    {
        var foundUser: UserModel?
        foundUser = users.find { p -> p.email == email }

        info("USER EMAIL + PASSWORD SIZE = $users.size")
        info("USER EMAIL + PASSWORD FOUND = $foundUser.email + $foundUser.password")

        if(foundUser != null)
        {


            if(email == foundUser.email && password == foundUser.password)
            {

                info("USER EMAIL + PASSWORD FOUND = $foundUser.email + $foundUser.password")
                info("USER EMAIL + PASSWORD SUPPLIED = $email + $password")
                return foundUser
            }
            return null
        }
        return null
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
            foundHillfort.visited = hillfort.visited
            logAll();
        }
    }

    override fun delete(hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }

        if(foundHillfort != null)
        {
            hillforts.remove(foundHillfort)
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(hillforts, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun serializeUsers() {
        val jsonString = gsonBuilder.toJson(users, listType2)
        write(context, USRJSON_FILE, jsonString)
    }

    private fun deserializeUsers() {
        val jsonString = read(context, USRJSON_FILE)
        users = Gson().fromJson(jsonString, listType2)
        info("USERS  = $users")
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        hillforts = Gson().fromJson(jsonString, listType)
    }

    fun logAll() {
        hillforts.forEach { info("${it}") }
    }
}