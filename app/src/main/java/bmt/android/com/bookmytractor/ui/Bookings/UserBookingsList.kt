package bmt.android.com.bookmytractor.ui.Bookings

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import bmt.android.com.bookmytractor.R
import bmt.android.com.bookmytractor.data.network.model.ApiController
import bmt.android.com.bookmytractor.data.network.model.ApiInterface
import bmt.android.com.bookmytractor.data.prefs.UserPrefs
import bmt.android.com.bookmytractor.databinding.ActivtyCreateAccountBinding
import bmt.android.com.bookmytractor.databinding.UserDetailsContentBinding
import bmt.android.com.bookmytractor.helper.CustomProgressBar
import bmt.android.com.bookmytractor.ui.login.CreateAccountActivity
import bmt.android.com.bookmytractor.ui.otpscreen.VerifyOtpActivity
import bmt.android.com.bookmytractor.utils.CommUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class UserBookingsList : AppCompatActivity() {

    var userName: String? = null
    var userMobile: String? = null
    var userEmail: String? = " "
    var disposable: Disposable? = null
    var userDetailsContentBinding: UserDetailsContentBinding? = null
    var userPrefs: UserPrefs? = null
    var customProgressBar: CustomProgressBar? = null
    private val TAG: String = UserBookingsList::class.java.simpleName
    var dateFrom: Boolean? = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userDetailsContentBinding = DataBindingUtil.setContentView(this, R.layout.user_details_content)

        userDetailsContentBinding!!.dateFrom.inputType = InputType.TYPE_NULL
        userDetailsContentBinding!!.dateTo.inputType = InputType.TYPE_NULL

        userDetailsContentBinding!!.dateFrom.setOnClickListener {
            dateFrom = true
            openDatePickerDialog()
        }

        userDetailsContentBinding!!.dateTo.setOnClickListener {
            dateFrom = false
            openDatePickerDialog()
        }

    }

    fun Activity.hideKeyboard() {
        hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun openDatePickerDialog() {

        var date: Calendar = Calendar.getInstance()
        var thisAYear = date.get(Calendar.YEAR)
        var thisAMonth = date.get(Calendar.MONTH)
        var thisADay = date.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view2, thisYear, thisMonth, thisDay ->
            // Display Selected date in textbox
            thisAMonth = thisMonth + 1
            thisADay = thisDay
            thisAYear = thisYear

            if (this.dateFrom!!)
                userDetailsContentBinding!!.dateFrom.setText(thisAMonth.toString() + "/" + thisDay + "/" + thisYear)
            else
                userDetailsContentBinding!!.dateFrom.setText(thisAMonth.toString() + "/" + thisDay + "/" + thisYear)

//            val newDate:Calendar =Calendar.getInstance()
//            newDate.set(thisYear, thisMonth, thisDay)
            //   mh.entryDate = newDate.timeInMillis // setting new date
        }, thisAYear, thisAMonth, thisADay)
        dpd.show()
    }

    private fun signUpApi() {

        disposable =
                ApiController.client?.create(ApiInterface::class.java)?.getSignUpResponse(this!!.userName!!, this!!.userEmail!!, this!!.userMobile!!, "bmt")?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
                        { result ->
                            customProgressBar!!.hideProgress()
                            if (result.success!!) {
                                val userPrefs = UserPrefs(this)
                                userPrefs!!.run {
                                    setUserEmail(result.user!!.email!!)
                                    setUserId(result.user!!.userId!!)
                                    setUserMobile(result.user!!.mobile!!)
                                    setUserName(result.user!!.name!!)
                                }
                                startActivity(Intent(applicationContext, VerifyOtpActivity::class.java))
                            }

                        }
                ) { error ->
                    customProgressBar!!.hideProgress()
                    if (error.message!!.contains("409"))
                        CommUtils(this).showToastMessage("User already exists")
                    // Toast.makeText(this, , Toast.LENGTH_SHORT).show()
                }
    }
}