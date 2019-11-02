package org.ab20075908.hillforts.activities

import android.app.ListActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_login.*
import org.ab20075908.hillforts.R
import org.ab20075908.hillforts.helpers.readImage
import org.ab20075908.hillforts.helpers.readImageFromPath
import org.ab20075908.hillforts.models.HillfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.ab20075908.hillforts.main.MainApp
import org.ab20075908.hillforts.helpers.showImagePicker
import org.ab20075908.hillforts.models.Location
import org.jetbrains.anko.intentFor

class LoginActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        info("Login Activity started..")

        app = application as MainApp


        btnLogin.setOnClickListener {
            info("LOGIN BUTTON")
            onLoginClick()

        }

        btnRegister.setOnClickListener {
            info("REGISTER BUTTON")
            onRegisterClick()

        }


    }



    fun onLoginClick() {

        var user = app.hillforts.login(emailField.text.toString(), passwordField.text.toString())

        info("USER EMAIL + PASSWORD = $emailField.text.toString() + $passwordField.text.toString()")
        if(user != null) {
            val intent = Intent(applicationContext, HillfortListActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    fun onRegisterClick() {

        app.hillforts.createUser(emailField.text.toString(), passwordField.text.toString())

    }
}