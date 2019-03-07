package bmt.android.com.bookmytractor.data.model.CreateAccountPojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignUpResponse {

    @SerializedName("otp_page")
    @Expose
    var otpPage: Boolean? = null

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("user")
    @Expose
    var user: User? = null

    @SerializedName("valid_for")
    @Expose
    var validFor: Long? = null

    @SerializedName("message")
    @Expose
    var message: String? = null



}
