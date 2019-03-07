package bmt.android.com.bookmytractor.utils

import android.content.Context
import android.widget.Toast

class CommUtils(context: Context)
{
    val mContext:Context=context

    fun showToastMessage(message:String)
    {
        Toast.makeText(mContext,message, Toast.LENGTH_SHORT)
                .show()
    }
}