package org.ab20075908.hillforts.views.hillfort

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.ab20075908.hillforts.R
import org.ab20075908.hillforts.helpers.readImageFromPath
import org.ab20075908.hillforts.models.HillfortModel
import org.ab20075908.hillforts.models.Location
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast


import org.ab20075908.hillforts.views.BaseView


class HillfortView : BaseView(), AnkoLogger {

    lateinit var presenter: HillfortPresenter
    //lateinit var map: GoogleMap
    var hillfort = HillfortModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)

        super.init(toolbarAdd, true)

        presenter = initPresenter (HillfortPresenter(this)) as HillfortPresenter

        chooseImage.setOnClickListener { presenter.doSelectImage() }

        imageLeft.setOnClickListener{presenter.decrementImageSelected()}
        imageRight.setOnClickListener{presenter.incrementImageSelected()}

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            presenter.doConfigureMap(it)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }
    }

    override fun showHillfort(hillfort: HillfortModel) {
        hillfortTitle.setText(hillfort.title)
        description.setText(hillfort.description)
        //Glide.with(this).load(hillfort.image1).dontAnimate().into(hillfortImage1);
        Glide.with(this).load(hillfort.image1).placeholder(R.drawable.witlogo).dontAnimate().into(hillfortImage1);
       /* if(hillfortImage2 != null)
        Glide.with(this).load(hillfort.image2).dontAnimate().into(hillfortImage2);
        if(hillfortImage3 != null)
        Glide.with(this).load(hillfort.image3).dontAnimate().into(hillfortImage3);
        if(hillfortImage4 != null)
        Glide.with(this).load(hillfort.image4).dontAnimate().into(hillfortImage4);
        */

        if (hillfort.image1 != null) {
            chooseImage.setText(R.string.change_hillfort_image)
        }
        this.showLocation(hillfort.location)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
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
        presenter.doResartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }


    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_save -> {
                if (hillfortTitle.text.toString().isEmpty()) {
                    toast(R.string.enter_hillfort_title)
                } else {
                    presenter.doAddOrSave(hillfortTitle.text.toString(), description.text.toString())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        presenter.doCancel()
    }

    override fun showLocation(location: Location) {
        lat.setText("%.6f".format(location.lat))
        lng.setText("%.6f".format(location.lng))
    }

    override fun showSelectedImage(selection: Int) {
        imageNo.setText("$selection");
    }
}