package org.ab20075908.hillforts.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.ab20075908.hillforts.main.MainApp

import org.ab20075908.hillforts.models.HillfortModel
import org.ab20075908.hillforts.views.BasePresenter
import org.ab20075908.hillforts.views.BaseView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

//Presenter for Hillfort Maps

class HillfortMapPresenter(view: BaseView) : BasePresenter(view) {

    //Populates map
    fun doPopulateMap(map: GoogleMap, hillforts: List<HillfortModel>) {
        map.uiSettings.setZoomControlsEnabled(true)
        hillforts.forEach {
            val loc = LatLng(it.location.lat, it.location.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))

        }
    }

    //Upon selection of marker, show the hillfort in question
    fun doMarkerSelected(marker: Marker) {
        val hillfort = marker.tag as HillfortModel
        doAsync {

            uiThread {
                if (hillfort != null) view?.showHillfort(hillfort)
            }
        }
    }

    //Loads all hillforts
    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    }
}