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

class HillfortListPresenter(view: BaseView) : BasePresenter(view) {

    fun doAddHillfort() {
        view?.navigateTo(VIEW.HILLFORT)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun doShowHillfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        view?.navigateTo(VIEW.LOGIN)
    }
}
