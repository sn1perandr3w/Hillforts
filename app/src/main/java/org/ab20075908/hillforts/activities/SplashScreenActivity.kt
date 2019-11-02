package org.ab20075908.hillforts.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.ab20075908.hillforts.R

//Based on Splash Screen Example by Devdeeds.com


class SplashScreenActivity : AppCompatActivity() {
    //Creates handler for runnable
    var mDelayHandler: Handler? = null
    //Splashscreen delay
    val SPLASH_DELAY: Long = 3000



    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            val intent = Intent(applicationContext, LoginActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Stops it from breaking if tabbing out of application
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }
}