package bmt.android.com.bookmytractor.ui.Splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import bmt.android.com.bookmytractor.R
import bmt.android.com.bookmytractor.ui.login.LoginActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        Timer("finishingSplash", false).schedule(500) {
           finish()
            val intent=Intent(applicationContext,LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}
