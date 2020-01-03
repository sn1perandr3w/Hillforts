package org.ab20075908.hillforts.views.login

import com.google.firebase.auth.FirebaseAuth
import org.ab20075908.hillforts.models.firebase.HillfortFireStore
import org.ab20075908.hillforts.views.BasePresenter
import org.ab20075908.hillforts.views.BaseView
import org.ab20075908.hillforts.views.VIEW
import org.jetbrains.anko.toast

//Presenter for Login page

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: HillfortFireStore? = null

    init {
        if (app.hillforts is HillfortFireStore) {
            fireStore = app.hillforts as HillfortFireStore
        }
    }

    //Login using firebase
    fun doLogin(email: String, password: String) {
        view?.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchHillforts {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                } else {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LIST)
                }
            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }

    //Sign up using firebase
    fun doSignUp(email: String, password: String) {
        view?.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                view?.hideProgress()
                view?.navigateTo(VIEW.LIST)
            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }


    //Used in main menu to prevent bug of a user being left signed in when closing the app externally.
    fun doLogout() {
        var user = FirebaseAuth.getInstance().currentUser

        if(user != null) {
            FirebaseAuth.getInstance().signOut()
            app.hillforts.clear()
            view?.navigateTo(VIEW.LOGIN)
        }
    }
}