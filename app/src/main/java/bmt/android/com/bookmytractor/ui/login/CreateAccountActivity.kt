package bmt.android.com.bookmytractor.ui.login

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bmt.android.com.bookmytractor.R
import bmt.android.com.bookmytractor.data.network.model.ApiController
import bmt.android.com.bookmytractor.data.network.model.ApiInterface
import bmt.android.com.bookmytractor.data.prefs.UserPrefs
import bmt.android.com.bookmytractor.databinding.ActivtyCreateAccountBinding
import bmt.android.com.bookmytractor.ui.otpscreen.VerifyOtpActivity
import bmt.android.com.bookmytractor.utils.CommUtils
import bmt.android.com.bookmytractor.helper.CustomProgressBar
import bmt.android.com.bookmytractor.utils.ValidationUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CreateAccountActivity : AppCompatActivity() {

    var userName: String? = null
    var userMobile: String? = null
    var userEmail: String? = " "
    var disposable: Disposable? = null
    var accountBinding: ActivtyCreateAccountBinding? = null
    var userPrefs: UserPrefs? = null
    var customProgressBar: CustomProgressBar? = null
    private val TAG: String = CreateAccountActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        accountBinding = DataBindingUtil.setContentView(this, R.layout.activty_create_account)
        accountBinding!!.alreadyMemberTxt.setOnClickListener { finish() }
        accountBinding!!.createAccountBtn.setOnClickListener { createUserAccount() }
        userPrefs = UserPrefs(this)
        customProgressBar= CustomProgressBar(this)

    }

//    val clickListener= View.OnClickListener {view->
//        when(view.id)
//        {
//            R.id.alreadyMemberTxt -> createUserAccount()
//            R.id.createAccountBtn->secndRun()
//        }
//    }

    private fun createUserAccount() {
        userMobile = accountBinding!!.editTextMobile.text.toString()
        userName = accountBinding!!.editTextName.text.toString()
        userEmail = accountBinding!!.editTextEmail.text.toString()

        if (!validateMobile()) {
            return
        }

        if (!validateName()) {
            return
        }

//        if (!validatePassword()) {
//            return
//        }

        customProgressBar!!.showProgressBar("Creating your profile....")
        signUpApi()
    }

    private fun validateMobile(): Boolean {
        if (userMobile!!.length < 10) {
            accountBinding!!.textInputLayoutMobile.error = getString(R.string.err_msg_mobile)
            ValidationUtils(this).requestFocus(accountBinding!!.editTextMobile)
            return false
        }
        return true
    }

//    private fun validatePassword(): Boolean {
//        if (userPwd!!.length < 6) {
//            accountBinding!!.textInputLayoutPwd.error = getString(R.string.err_msg_pwd);
//            ValidationUtils(this).requestFocus(accountBinding!!.editTextPwd)
//            return false
//        }
//        return true
//    }

    private fun validateName(): Boolean {
        if (userName!!.isEmpty()) {
            accountBinding!!.textInputLayoutName.error = getString(R.string.err_msg_name);
            ValidationUtils(this).requestFocus(accountBinding!!.editTextName)
            return false
        }
        return true
    }


    private fun signUpApi()
    {

        disposable =
                ApiController.client?.create(ApiInterface::class.java)?.getSignUpResponse(this!!.userName!!, this!!.userEmail!!, this!!.userMobile!!, "bmt")?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
                        { result ->
                            customProgressBar!!.hideProgress()
                            if (result.success!!)
                            {
                                val userPrefs=UserPrefs(this)
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
                    if(error.message!!.contains("409"))
                        CommUtils(this).showToastMessage("User already exists")
                       // Toast.makeText(this, , Toast.LENGTH_SHORT).show()
                }
    }
}
