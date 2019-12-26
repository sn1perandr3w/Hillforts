package org.ab20075908.hillforts.activities


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.ab20075908.hillforts.R
import org.ab20075908.hillforts.main.MainApp
import org.ab20075908.hillforts.models.HillfortModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

//Standard ListActivity from Placemark aside of additional options to menu at top
//Menu Bar is commented below

class HillfortListActivity : AppCompatActivity(), HillfortListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadHillforts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Menu bar to allow user to add Hillfort, go to settings or log out.
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<HillfortActivity>(200)
            R.id.item_map -> startActivity<HillfortMapsActivity>()
            R.id.item_settings -> startActivityForResult<SettingsActivity>(0)
            R.id.item_logout -> startActivityForResult<LoginActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
        startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
    }

    private fun loadHillforts() {
        showHillforts(app.hillforts.findAll())
    }


    fun showHillforts (hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}