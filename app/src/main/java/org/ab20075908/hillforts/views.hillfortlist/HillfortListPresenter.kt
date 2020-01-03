package org.ab20075908.hillforts.views.hillfortlist

import com.google.firebase.auth.FirebaseAuth
import org.ab20075908.hillforts.views.map.HillfortMapView
import org.ab20075908.hillforts.views.hillfort.HillfortView
import org.ab20075908.hillforts.main.MainApp
import org.ab20075908.hillforts.models.HillfortModel


import org.ab20075908.hillforts.views.BasePresenter
import org.ab20075908.hillforts.views.BaseView
import org.ab20075908.hillforts.views.VIEW
import org.jetbrains.anko.*

//Presenter for HillfortList

class HillfortListPresenter(view: BaseView) : BasePresenter(view) {

    //Begins adding hillfort
    fun doAddHillfort() {
        view?.navigateTo(VIEW.HILLFORT)
    }

    //Begins editing hillfort (Same view as doAddHillfort but uses "hillfort_edit"
    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    //Shows map
    fun doShowHillfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    //Shows hillforts from retrieval using findAll
    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    }

    //Logs out user
    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        view?.navigateTo(VIEW.LOGIN)
    }
}
