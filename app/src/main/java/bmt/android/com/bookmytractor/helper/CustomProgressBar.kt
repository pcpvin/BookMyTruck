package bmt.android.com.bookmytractor.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ProgressBar
import android.widget.TextView
import bmt.android.com.bookmytractor.R

class CustomProgressBar(private val mContext: Context)
{
    private var mDialog: Dialog? = null
    private var title: TextView? = null
    private val mTitle: String? = null



    fun showProgressBar(mTitle: String)
    {
       // Log.d(TAG, "showProgressBar: ")
        if (mDialog == null) mDialog = Dialog(mContext)
        mDialog!!.setContentView(R.layout.custom_progres_bar)
        mDialog!!.findViewById(R.id.progress_bar) as ProgressBar
        title = mDialog!!.findViewById(R.id.progress_bar_title) as TextView
        title!!.text = mTitle
        mDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog!!.setCancelable(true)
        mDialog!!.setCanceledOnTouchOutside(false)
        mDialog!!.show()

    }

    fun hideProgress() {
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.dismiss()
            mDialog = null
        }
    }
}