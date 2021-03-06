package org.ab20075908.hillforts.room

import android.content.Context
import androidx.room.Room
import org.ab20075908.hillforts.models.HillfortModel
import org.ab20075908.hillforts.models.HillfortStore

import org.ab20075908.hillforts.models.room.Database

class HillfortStoreRoom(val context: Context) : HillfortStore {


  override fun clear() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  var dao: HillfortDao

  init {
    val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
        .fallbackToDestructiveMigration()
        .build()
    dao = database.hillfortDao()
  }

  override fun findAll(): List<HillfortModel> {
    return dao.findAll()
  }

  override fun findById(id: Long): HillfortModel? {
    return dao.findById(id)
  }

  override fun create(hillfort: HillfortModel) {
    dao.create(hillfort)
  }

  override fun update(hillfort: HillfortModel) {
    dao.update(hillfort)
  }

  override fun delete(hillfort: HillfortModel) {
    dao.deleteHillfort(hillfort)
  }

}