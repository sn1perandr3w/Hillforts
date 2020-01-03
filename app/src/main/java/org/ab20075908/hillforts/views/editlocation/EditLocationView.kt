package org.ab20075908.hillforts.views.editlocation


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import org.ab20075908.hillforts.R


import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_edit_location.*



import org.ab20075908.hillforts.views.BaseView

//View for EditLocation

class EditLocationView : BaseView(), GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    lateinit var presenter: EditLocationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_location)
        super.init(toolbar, true)

        presenter = initPresenter(EditLocationPresenter(this)) as EditLocationPresenter

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            it.setOnMarkerDragListener(this)
            it.setOnMarkerClickListener(this)
            presenter.doConfigureMap(it)
        }

        lat.setText("%.6f".format(presenter.location.lat))
        lng.setText("%.6f".format(presenter.location.lng))


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit_location, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_save -> {
                presenter.doSave()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMarkerDragStart(marker: Marker) {}

    override fun onMarkerDrag(marker: Marker) {
        lat.setText("%.6f".format(marker.position.latitude))
        lng.setText("%.6f".format(marker.position.longitude))
    }

    override fun onMarkerDragEnd(marker: Marker) {
        presenter.doUpdateLocation(marker.position.latitude, marker.position.longitude)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doUpdateMarker(marker)
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
