package bmt.android.com.bookmytractor.data.prefs

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color

class UserPrefs(context: Context)
{

    private val PREF_NAME = "bmt"
    // Shared preferences file name
    private val USER_ID = "user_id"
    private val USER_NAME = "user_name"
    private val USER_EMAIL = "user_email"
    private val USER_MOBILE = "user_mobile"
    private val USER_ACCESS_TOKEN = "access_token"
    private val pref=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    private val TAG = UserPrefs::class.java.simpleName

    fun clearAll()
    {
        val editor= pref.edit()
        editor.remove(USER_ID)
        editor.remove(USER_NAME)
        editor.remove(USER_EMAIL)
        editor.remove(USER_ACCESS_TOKEN)
        editor.apply()
    }


    fun setUserId(userId: Long?)
    {
        val editor= pref.edit()
        editor.putLong(USER_ID, userId!!)
        editor.commit()
    }


    fun getUserId(): Long? {
        return pref.getLong(USER_ID, 0)
    }

    fun setUserEmail(email: String?) {
        val editor= pref.edit()
        editor.putString(USER_EMAIL, email!!)
        editor.commit()
    }

    fun getUserEmail(): String {
        return pref.getString(USER_EMAIL, "")
    }

    fun setUserMobile(mobile: String) {
        val editor= pref.edit()
        editor.putString(USER_MOBILE, mobile)
        editor.commit()
    }

    fun getUserMobile(): String? {
        return pref.getString(USER_MOBILE, "")
    }


    fun setUserAccessToken(accessToken: String?) {
        val editor= pref.edit()
        editor.putString(USER_ACCESS_TOKEN, accessToken)
        editor.commit()
    }

    fun getUserAccessToken(): String? {
        return pref.getString(USER_ACCESS_TOKEN, "")
    }

    fun setUserName(name: String?) {
        val editor= pref.edit()
        editor.putString(USER_NAME, name)
        editor.commit()
    }

    fun getUserName(): String? {
        return pref.getString(USER_NAME, "")
    }

}