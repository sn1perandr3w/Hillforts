package org.ab20075908.hillforts.views.login

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import org.ab20075908.hillforts.R
import org.ab20075908.hillforts.views.BaseView

//View for Login Page

class LoginView : BaseView() {

    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        init(toolbar, false)
        progressBar.visibility = View.GONE


        presenter = initPresenter(LoginPresenter(this)) as LoginPresenter

        //Listener for signing up using Firebase authentication
        signUp.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")

            }
            else {
                presenter.doSignUp(email,password)
            }
        }

        //Listener for logging in using Firebase authentication
        logIn.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")

            }
            else {
                presenter.doLogin(email,password)
            }
        }
        presenter.doLogout()
    }

    //Progress bar visibility
    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }
}
