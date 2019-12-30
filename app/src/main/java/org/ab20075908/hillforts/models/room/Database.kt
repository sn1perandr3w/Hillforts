package org.ab20075908.hillforts.models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.ab20075908.hillforts.models.HillfortModel
import org.ab20075908.hillforts.room.HillfortDao

@Database(entities = arrayOf(HillfortModel::class), version = 1,  exportSchema = false)
abstract class Database : RoomDatabase() {

  abstract fun hillfortDao(): HillfortDao
}