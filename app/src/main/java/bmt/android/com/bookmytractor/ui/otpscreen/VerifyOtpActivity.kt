package bmt.android.com.bookmytractor.ui.otpscreen

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import bmt.android.com.bookmytractor.R
import bmt.android.com.bookmytractor.data.network.model.ApiController
import bmt.android.com.bookmytractor.data.network.model.ApiInterface
import bmt.android.com.bookmytractor.data.prefs.UserPrefs
import bmt.android.com.bookmytractor.databinding.VerifyOtpScreenBinding
import bmt.android.com.bookmytractor.ui.UserPasswordsScreen.AddNewPasswordActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import android.util.Log
import bmt.android.com.bookmytractor.data.constants.UserConstants
import bmt.android.com.bookmytractor.utils.CommUtils
import bmt.android.com.bookmytractor.helper.CustomProgressBar
import java.util.concurrent.TimeUnit
import kotlin.math.log


class VerifyOtpActivity : AppCompatActivity()
{
    var verifyOtpScreenBinding: VerifyOtpScreenBinding? = null
    var disposable: Disposable? = null
    var customProgressBar: CustomProgressBar? = null
    var userPrefs: UserPrefs? = null
    private val TAG: String = VerifyOtpActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyOtpScreenBinding = DataBindingUtil.setContentView(this, R.layout.verify_otp_screen)
        verifyOtpScreenBinding!!.resendOtp.isEnabled = false
        verifyOtpScreenBinding!!.resendOtp.isClickable = false
        customProgressBar = CustomProgressBar(this)
        userPrefs = UserPrefs(this)

        startTimer()

        verifyOtpScreenBinding!!.createPwdBtn.setOnClickListener {

            if (verifyOtpScreenBinding!!.otpVerification.text.toString().isEmpty())
                CommUtils(this).showToastMessage("Enter otp first")
            else {
                customProgressBar!!.showProgressBar("Verifying otp")
                verifyOtpApi()
            }

        }

        verifyOtpScreenBinding!!.resendOtp.setOnClickListener {
            startTimer()
            forgotPwdApi()
        }
    }


    private fun startTimer() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                var time: Long = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                verifyOtpScreenBinding!!.resendOtp.setText("Resend in-" + time)

            }

            override fun onFinish() {
                verifyOtpScreenBinding!!.resendOtp.isEnabled = true
                verifyOtpScreenBinding!!.resendOtp.isClickable = true
                verifyOtpScreenBinding!!.resendOtp.setText("Resend")
            }
        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        verifyOtpScreenBinding!!.unbind()
    }

    private fun verifyOtpApi()
    {

        disposable =
                ApiController.client?.create(ApiInterface::class.java)?.getOtpVerificationResponse(verifyOtpScreenBinding!!.otpVerification.text.toString(), UserPrefs(this).getUserId()!!)!!.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
                        { result ->
                            if (result.success!!)
                            {
                                customProgressBar!!.hideProgress()
                                UserPrefs(this).setUserAccessToken(result.accessToken)
                                UserConstants.ACESS_TOKEN=result.accessToken
                                startActivity(Intent(applicationContext, AddNewPasswordActivity::class.java))

                            }
                        }
                ) { error ->
                    customProgressBar!!.hideProgress()
                    CommUtils(this).showToastMessage(error.message!!)
                }
    }

    private fun forgotPwdApi() {

        disposable =
                ApiController.client?.create(ApiInterface::class.java)?.getForgotPwdResponse(UserPrefs(this).getUserId().toString())!!.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
                        { result ->
                            customProgressBar!!.hideProgress()
                            if (result.success!!)
                            {
                                 val userPrefs=UserPrefs(this)
                                 userPrefs.setUserId(result.user!!.get(0).id!!)
                            }
                            else CommUtils(this).showToastMessage(result.message!!)
                        },
                        { error ->
                            customProgressBar!!.hideProgress()
                            CommUtils(this).showToastMessage("Invalid otp")
                        }
                )
    }

}