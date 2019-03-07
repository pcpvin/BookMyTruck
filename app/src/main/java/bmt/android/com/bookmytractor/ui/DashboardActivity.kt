package bmt.android.com.bookmytractor.ui

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bmt.android.com.bookmytractor.R
import bmt.android.com.bookmytractor.databinding.ActivityDashboardBinding
import bmt.android.com.bookmytractor.ui.Bookings.UserBookingsList
import bmt.android.com.bookmytractor.ui.UserPasswordsScreen.AddNewPasswordActivity

class DashboardActivity : AppCompatActivity()
{

    var activityDashboardBinding: ActivityDashboardBinding?= null


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        activityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        activityDashboardBinding!!.newBookingCard.setOnClickListener {
            startActivity(Intent(applicationContext, CropListActivity::class.java))
        }

        activityDashboardBinding!!.yourBookingCard.setOnClickListener {
            startActivity(Intent(applicationContext, UserBookingsList::class.java))
        }
    }
}
