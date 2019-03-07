package bmt.android.com.bookmytractor.ui.login

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import bmt.android.com.bookmytractor.R
import bmt.android.com.bookmytractor.data.network.model.ApiController
import bmt.android.com.bookmytractor.data.network.model.ApiInterface
import bmt.android.com.bookmytractor.data.prefs.UserPrefs
import bmt.android.com.bookmytractor.databinding.ActivityLoginBinding
import bmt.android.com.bookmytractor.ui.DashboardActivity
import bmt.android.com.bookmytractor.ui.UserPasswordsScreen.ForgotPwdActivity
import bmt.android.com.bookmytractor.ui.otpscreen.VerifyOtpActivity
import bmt.android.com.bookmytractor.utils.CommUtils
import bmt.android.com.bookmytractor.utils.NetworkUtils
import bmt.android.com.bookmytractor.utils.ValidationUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginActivity : AppCompatActivity() {

    private var userMobile: String? = null
    private var userPwd: String? = null
    private var binding: ActivityLoginBinding? = null
    var disposable: Disposable? = null
    var userPrefs: UserPrefs? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding!!.noAccountYet.setOnClickListener { startActivity(Intent(applicationContext, CreateAccountActivity::class.java)) }
        binding!!.loginBtn.setOnClickListener { if (NetworkUtils.isNetworkConnected(applicationContext)) submitUserData() }
        binding!!.forgotPwd.setOnClickListener {
            startActivity(Intent(applicationContext, ForgotPwdActivity::class.java))
        }

    }

    private fun submitUserData()
    {
        userMobile = binding!!.editTextMobile.text.toString()
        userPwd = binding!!.editTextPwd.text.toString()

        if (!validateMobile()) {
            return
        }

        if (!validatePassword()) {
            return
        }


        userLoginApi()
    }


    private fun validateMobile(): Boolean {
        if (userMobile!!.length < 10) {
            binding!!.textInputLayoutMobile.error = getString(R.string.err_msg_mobile)
            ValidationUtils(this).requestFocus(binding!!.editTextMobile)
            return false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        if (userPwd!!.length < 6) {
            binding!!.textInputLayoutPwd.error = getString(R.string.err_msg_pwd);
            ValidationUtils(this).requestFocus(binding!!.editTextPwd)
            return false
        }
        return true
    }

    private fun userLoginApi()
    {

        disposable =
                ApiController.client?.create(ApiInterface::class.java)?.getLoginResponse(userMobile,userPwd)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
                        { result ->
                            if (result.success!!)
                            {
                                val userPrefs=UserPrefs(this)
                                userPrefs.setUserEmail(result.user!!.email!!)
                                userPrefs.setUserId(result.user!!.userId!!)
                                userPrefs.setUserMobile(result.user!!.mobile!!)
                                userPrefs.setUserName(result.user!!.name!!)
                                startActivity(Intent(applicationContext, DashboardActivity::class.java))
                            }
                        }
                ) { error ->
                    CommUtils(this).showToastMessage(error.message!!)
                }
    }


}