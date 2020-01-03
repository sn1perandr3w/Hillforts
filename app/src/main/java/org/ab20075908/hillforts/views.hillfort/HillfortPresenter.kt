package org.ab20075908.hillforts.views.hillfort


import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.CheckBox
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.ab20075908.hillforts.R
import org.ab20075908.hillforts.helpers.checkLocationPermissions
import org.ab20075908.hillforts.helpers.createDefaultLocationRequest
import org.ab20075908.hillforts.helpers.isPermissionGranted
import org.ab20075908.hillforts.helpers.showImagePicker
import org.ab20075908.hillforts.models.Location
import org.ab20075908.hillforts.models.HillfortModel
import org.ab20075908.hillforts.views.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread

//Presenter for Hillfort

class HillfortPresenter(view: BaseView) : BasePresenter(view) {

    var map: GoogleMap? = null
    var hillfort = HillfortModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var edit = false;
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    val locationRequest = createDefaultLocationRequest()
    var IMAGE_NO = 1


    init {
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
        } else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(Location(it.latitude, it.longitude))
        }
    }

    @SuppressLint("MissingPermission")
    fun doRestartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(Location(l.latitude, l.longitude))
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }


    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            locationUpdate(defaultLocation)
        }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(hillfort.location)
    }

    //Updating location
    fun locationUpdate(location: Location) {
        hillfort.location = location
        hillfort.location.zoom = 15f
        map?.clear()
        val options = MarkerOptions().title(hillfort.title).position(LatLng(hillfort.location.lat, hillfort.location.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.location.lat, hillfort.location.lng), hillfort.location.zoom))
        view?.showLocation(hillfort.location)
    }

    //Adding or saving hillfort
    fun doAddOrSave(title: String, description: String) {
        hillfort.title = title
        hillfort.description = description
        doAsync {
            if (edit) {
                app.hillforts.update(hillfort)
            } else {
                app.hillforts.create(hillfort)
            }
            uiThread {
                view?.finish()
            }
        }
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        doAsync {
            app.hillforts.delete(hillfort)
            uiThread {
                view?.finish()
            }
        }
    }

    fun doSelectImage() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doSetLocation() {
        view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(hillfort.location.lat, hillfort.location.lng, hillfort.location.zoom))
    }

    //parsing requests
    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {

                if(IMAGE_NO == 1)
                hillfort.image1 = data.data.toString()

                if(IMAGE_NO == 2)
                    hillfort.image2 = data.data.toString()

                if(IMAGE_NO == 3)
                    hillfort.image3 = data.data.toString()

                if(IMAGE_NO == 4)
                    hillfort.image4 = data.data.toString()

                view?.showHillfort(hillfort)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                hillfort.location = location
                locationUpdate(location)
            }
        }
    }

    //Increments number of which image will be set by the image picker
    fun incrementImageSelected()
    {
        if(IMAGE_NO < 4)
        {
            IMAGE_NO++;

        }
        view?.showSelectedImage(IMAGE_NO);
    }

    //Decrements number of which image will be set by the image picker

    fun decrementImageSelected()
    {

        if(IMAGE_NO > 1)
        {
            IMAGE_NO--;
        }
        view?.showSelectedImage(IMAGE_NO);
    }


    //Increments rating and updates in view
    fun incrementRating()
    {
        if(hillfort.rating < 5)
        {
            hillfort.rating++;

        }
        view?.showRating(hillfort.rating);
    }

    //Decrements rating and updates in view

    fun decrementRating()
    {

        if(hillfort.rating > 1)
        {
            hillfort.rating--;

        }
        view?.showRating(hillfort.rating);
    }

    //Changes checkbox boolean and updates in view

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkbox_visited -> {
                    if (checked) {

                        hillfort.visited = true
                    } else {

                        hillfort.visited = false
                    }
                }
            }
        }
    }

}