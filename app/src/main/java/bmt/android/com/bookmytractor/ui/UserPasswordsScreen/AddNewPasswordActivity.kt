package bmt.android.com.bookmytractor.ui.UserPasswordsScreen

import android.content.Intent
import android.databinding.DataBindingUtil
import android.nfc.Tag
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import bmt.android.com.bookmytractor.R
import bmt.android.com.bookmytractor.data.network.model.ApiController
import bmt.android.com.bookmytractor.data.network.model.ApiInterface
import bmt.android.com.bookmytractor.data.prefs.UserPrefs
import bmt.android.com.bookmytractor.databinding.ActivityAddNewPwdBinding
import bmt.android.com.bookmytractor.ui.DashboardActivity
import bmt.android.com.bookmytractor.utils.CommUtils
import bmt.android.com.bookmytractor.helper.CustomProgressBar
import bmt.android.com.bookmytractor.ui.login.CreateAccountActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class AddNewPasswordActivity : AppCompatActivity() {
    var addNewPwdBinding: ActivityAddNewPwdBinding? = null
    var disposable: Disposable? = null
    var customProgressBar: CustomProgressBar? = null
    private val TAG: String = AddNewPasswordActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addNewPwdBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_pwd)
        customProgressBar = CustomProgressBar(this)
        addNewPwdBinding!!.btnAddPwd.setOnClickListener {

            if (addNewPwdBinding!!.newPwd.text.toString().isEmpty())
                CommUtils(this).showToastMessage("passwords cannot be blank")
            else if (!addNewPwdBinding!!.confirmPwd.text.toString().contentEquals(addNewPwdBinding!!.newPwd.text.toString()))
                CommUtils(this).showToastMessage(" Both passwords must be same")
            else
            {
                Log.d(TAG, UserPrefs(this).getUserAccessToken()!!);
                customProgressBar!!.showProgressBar("Setting up passwords....")
                addPwdApi()
            }
        }

    }

    private fun addPwdApi()
    {
        disposable =
                ApiController.client?.create(ApiInterface::class.java)?.getAddPwdResponse(UserPrefs(this).getUserId(), addNewPwdBinding!!.newPwd.text.toString())!!.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
                        { result ->
                            customProgressBar!!.hideProgress()
                            if (result.success!!) {
                                startActivity(Intent(applicationContext, DashboardActivity::class.java))
                                CommUtils(this).showToastMessage(result.message!!)
                            }
                        }
                ) { error ->
                    customProgressBar!!.hideProgress()
                    CommUtils(this).showToastMessage(error.message!!)
                }
    }
}