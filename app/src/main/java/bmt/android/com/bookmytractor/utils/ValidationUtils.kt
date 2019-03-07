package bmt.android.com.bookmytractor.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import bmt.android.com.bookmytractor.R

class ValidationUtils(context: Context)
{

    private val mContext:Context = context

    fun isValidEmail(target: CharSequence?): Boolean {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun requestFocus(view: View) {
        if (view.requestFocus())
        {
            mContext as Activity
            mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }
}