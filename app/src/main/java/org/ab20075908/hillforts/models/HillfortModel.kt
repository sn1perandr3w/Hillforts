package org.ab20075908.hillforts.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HillfortModel(var id: Long = 0,
                         var title: String = "",
                         var image: String = "",
                         var description: String = "") : Parcelable