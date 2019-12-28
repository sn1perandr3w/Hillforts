package org.ab20075908.hillforts.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.ab20075908.hillforts.R
import org.ab20075908.hillforts.main.MainApp
import org.ab20075908.hillforts.models.UserModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult

class SettingsView : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        info("Settings Activity started..")

        app = application as MainApp

        toolbarSettings.title = title
        setSupportActionBar(toolbarSettings)


        emailEditField.setText(app.signedInUser.email)
        passwordEditField.setText(app.signedInUser.password)


        btnUpdate.setOnClickListener {
            info("UPDATE BUTTON")
            onUpdateClick()

        }

    }

    //Shows toolbar

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Using toolbar options

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_up -> finish()
            R.id.item_logout -> startActivityForResult<LoginView>(0)


        }
        return super.onOptionsItemSelected(item)
    }

    //Button to update user credentials

    fun onUpdateClick() {
        var currentUser = UserModel()
        currentUser.email = emailEditField.text.toString()
        currentUser.password = passwordEditField.text.toString()
        app.hillforts.updateCredentials(app.signedInUser,currentUser)
    }

}