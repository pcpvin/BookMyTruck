package bmt.android.com.bookmytractor.data.model.ForgotPasswordPojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("id")
    @Expose
    var id: Long? = null
    @SerializedName("mobile")
    @Expose
    var mobile: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("verified")
    @Expose
    var verified: String? = null

}
