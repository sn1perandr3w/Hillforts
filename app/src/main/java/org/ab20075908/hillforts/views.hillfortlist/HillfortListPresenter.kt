package org.ab20075908.hillforts.views.hillfortlist

import org.ab20075908.hillforts.views.map.HillfortMapView
import org.ab20075908.hillforts.views.hillfort.HillfortView
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.ab20075908.hillforts.main.MainApp
import org.ab20075908.hillforts.models.HillfortModel


import org.ab20075908.hillforts.views.BasePresenter
import org.ab20075908.hillforts.views.BaseView
import org.ab20075908.hillforts.views.VIEW

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
        view?.showHillforts(app.hillforts.findAll())
    }
}