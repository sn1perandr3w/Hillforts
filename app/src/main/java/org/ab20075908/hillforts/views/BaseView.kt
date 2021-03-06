package org.ab20075908.hillforts.views

import android.content.Intent

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger

import org.ab20075908.hillforts.models.HillfortModel
import org.ab20075908.hillforts.models.Location
import org.ab20075908.hillforts.views.editlocation.EditLocationView
import org.ab20075908.hillforts.views.map.HillfortMapView
import org.ab20075908.hillforts.views.hillfort.HillfortView
import org.ab20075908.hillforts.views.hillfortlist.HillfortListView

import org.ab20075908.hillforts.views.login.LoginView

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
  LOCATION, HILLFORT, MAPS, LIST,  LOGIN
}

//Base View

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {

  var basePresenter: BasePresenter? = null

  fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
    var intent = Intent(this, HillfortListView::class.java)
    when (view) {
      VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
      VIEW.HILLFORT -> intent = Intent(this, HillfortView::class.java)
      VIEW.MAPS -> intent = Intent(this, HillfortMapView::class.java)
      VIEW.LIST -> intent = Intent(this, HillfortListView::class.java)
      VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
    }
    if (key != "") {
      intent.putExtra(key, value)
    }
    startActivityForResult(intent, code)
  }

  fun initPresenter(presenter: BasePresenter): BasePresenter {
    basePresenter = presenter
    return presenter
  }

  fun init(toolbar: Toolbar, upEnabled: Boolean) {
    toolbar.title = title
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
      toolbar.title = "${title}: ${user.email}"
    }
  }

  override fun onDestroy() {
    basePresenter?.onDestroy()
    super.onDestroy()
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      basePresenter?.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  open fun showHillfort(hillfort: HillfortModel) {}
  open fun showHillforts(hillforts: List<HillfortModel>) {}
  open fun showProgress() {}
  open fun hideProgress() {}
  open fun showLocation(location : Location) {}
  open fun showSelectedImage(selection : Int) {}
  open fun showRating(rating: Int){}
  open fun showLocation(latitude: Double, longitude : Double){}
}