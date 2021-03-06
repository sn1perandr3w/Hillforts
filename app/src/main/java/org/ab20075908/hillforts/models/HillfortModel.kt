package org.ab20075908.hillforts.models

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize


import androidx.room.Entity
import androidx.room.PrimaryKey



@Parcelize
@Entity
data class HillfortModel(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                         var title: String = "",
                         var fbId : String = "",
                         var description: String = "",
                         var image1: String = "",
                         var image2: String = "",
                         var image3: String = "",
                         var image4: String = "",
                         var rating: Int = 1,
                         var visited: Boolean = false,
                         @Embedded var location : Location = Location()): Parcelable
@Parcelize
@Entity
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
/*
@Parcelize
@Entity
data class UserModel(var email: String = "",
                    var password: String = "") : Parcelable

 */