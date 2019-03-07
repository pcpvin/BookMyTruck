package bmt.android.com.bookmytractor.ui

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.Toast
import bmt.android.com.bookmytractor.R
import bmt.android.com.bookmytractor.data.network.model.ApiController
import bmt.android.com.bookmytractor.data.network.model.ApiInterface
import bmt.android.com.bookmytractor.data.prefs.UserPrefs
import bmt.android.com.bookmytractor.databinding.ActivityCropListBinding
import bmt.android.com.bookmytractor.helper.CustomProgressBar
import bmt.android.com.bookmytractor.helper.InfiniteScrollListener
import bmt.android.com.bookmytractor.helper.NpaGridlayoutManager
import bmt.android.com.bookmytractor.ui.UserPasswordsScreen.AddNewPasswordActivity
import bmt.android.com.bookmytractor.ui.UserPasswordsScreen.ForgotPwdActivity
import bmt.android.com.bookmytractor.ui.adapters.BtnClickListener
import bmt.android.com.bookmytractor.ui.adapters.CropListAdapter
import bmt.android.com.bookmytractor.utils.CommUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CropListActivity : AppCompatActivity() {

    var activityCropListBinding: ActivityCropListBinding? = null
    var cropListAdapter: CropListAdapter? = null
    var disposable: Disposable? = null
    var customProgressBar: CustomProgressBar? = null
    var userPrefs: UserPrefs? = null
    val items = ArrayList<String>()
    var offset: Int = 1
    var keyword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCropListBinding = DataBindingUtil.setContentView(this, R.layout.activity_crop_list)
        customProgressBar = CustomProgressBar(this)
        userPrefs = UserPrefs(this)
        customProgressBar!!.showProgressBar("Fetching crop list")

        fetchCropList()

        cropListAdapter= CropListAdapter(items,applicationContext,object :BtnClickListener
        {
            override fun onBtnClick(position: Int, cardView: CardView)
            {
                startActivity(Intent(applicationContext, MachineListActivity::class.java))
            }
        })

        //now adding the adapter to recyclerview
        activityCropListBinding!!.cropListRecycler?.adapter = cropListAdapter

        activityCropListBinding!!.cropListRecycler!!.addOnScrollListener(InfiniteScrollListener({
            fetchCropList()
        }, NpaGridlayoutManager(this, 2)))
    }

    private fun fetchCropList()
    {
        disposable =
                ApiController.client?.create(ApiInterface::class.java)?.getCropList(offset, 12, "")!!.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
                        { result ->
                            if (result.success!!) {
                                customProgressBar!!.hideProgress()
                                items.addAll(result.crops!!)
                                activityCropListBinding!!.cropListRecycler.layoutManager = NpaGridlayoutManager(this, 2)
                                cropListAdapter?.notifyItemRangeChanged(0, items.size)
                                offset++
                            }
                        }
                ) { error ->
                    customProgressBar!!.hideProgress()
                    CommUtils(this).showToastMessage(error.message!!)
                }
    }
}