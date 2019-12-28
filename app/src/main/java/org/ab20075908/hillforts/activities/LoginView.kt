package org.ab20075908.hillforts.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.ab20075908.hillforts.R
import org.ab20075908.hillforts.main.MainApp
import org.ab20075908.hillforts.models.UserModel
import org.ab20075908.hillforts.views.hillfortlist.HillfortListView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class LoginView : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        info("Login Activity started..")

        app = application as MainApp

        //User cannot be nullable due to being lateinit so this will help for signing out.
        app.signedInUser = UserModel()

        //Listeners

        btnLogin.setOnClickListener {
            info("LOGIN BUTTON")
            onLoginClick()

        }

        btnRegister.setOnClickListener {
            info("REGISTER BUTTON")
            onRegisterClick()

        }


    }

    //Retrieves user based on input credentials. If not null, log in. If null, do not.

    fun onLoginClick() {

        var user = app.hillforts.login(emailField.text.toString(), passwordField.text.toString())

        info("USER EMAIL + PASSWORD = $emailField.text.toString() + $passwordField.text.toString()")
        if(user != null) {
            val intent = Intent(applicationContext, HillfortListView::class.java)
            finish()
            app.signedInUser = user
            startActivity(intent)
        }
    }

    //Creates user based on input credentials.

    fun onRegisterClick() {

        app.hillforts.createUser(emailField.text.toString(), passwordField.text.toString())

    }
}