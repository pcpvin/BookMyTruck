package bmt.android.com.bookmytractor.ui.UserPasswordsScreen

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bmt.android.com.bookmytractor.R
import bmt.android.com.bookmytractor.data.constants.UserConstants
import bmt.android.com.bookmytractor.data.network.model.ApiController
import bmt.android.com.bookmytractor.data.network.model.ApiInterface
import bmt.android.com.bookmytractor.data.prefs.UserPrefs
import bmt.android.com.bookmytractor.databinding.ActivityForgotPwdBinding
import bmt.android.com.bookmytractor.ui.otpscreen.VerifyOtpActivity
import bmt.android.com.bookmytractor.utils.CommUtils
import bmt.android.com.bookmytractor.helper.CustomProgressBar
import bmt.android.com.bookmytractor.utils.ValidationUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ForgotPwdActivity:AppCompatActivity()
{

    var forgotPwdBinding:ActivityForgotPwdBinding?=null
    var disposable:Disposable?=null
    var userMobile: String? = null
    var customProgressBar: CustomProgressBar?=null
    private val TAG: String = ForgotPwdActivity::class.java.simpleName
    var userPrefs:UserPrefs?=null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        forgotPwdBinding= DataBindingUtil.setContentView(this, R.layout.activity_forgot_pwd)
        customProgressBar= CustomProgressBar(this)

        forgotPwdBinding!!.btnGetOtp.setOnClickListener {

            customProgressBar!!.showProgressBar("Sending otp")
            if (validateMobile()) {
                forgotPwdApi()
            }
        }
    }


    private fun validateMobile(): Boolean
    {
        userMobile= forgotPwdBinding!!.enterRegisteredMobile.text.toString()
        UserConstants.USER_MOBILE = userMobile as String
        if (userMobile!!.length < 10) {
            forgotPwdBinding!!.textInputLayoutMobile.error = getString(R.string.err_msg_mobile)
            ValidationUtils(this).requestFocus(forgotPwdBinding!!.enterRegisteredMobile)
            return false
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        forgotPwdBinding!!.unbind()
    }


    private fun forgotPwdApi()
    {
        disposable =
                ApiController.client?.create(ApiInterface::class.java)?.getForgotPwdResponse(this.userMobile!!)!!.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
                        { result ->
                            customProgressBar!!.hideProgress()
                            if (result.success!!) {
                                val userPrefs=UserPrefs(this)
                                userPrefs!!.setUserId( result.user!!.get(0).id!!)
                                result.user!!.get(0).id!!.toLong()
                                startActivity(Intent(applicationContext, VerifyOtpActivity::class.java))}
                        },
                        { error ->
                            customProgressBar!!.hideProgress()
                            CommUtils(this).showToastMessage(error.message!!)
                        }
                )
    }
}