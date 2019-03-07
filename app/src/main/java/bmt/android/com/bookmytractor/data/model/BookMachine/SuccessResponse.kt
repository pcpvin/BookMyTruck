package bmt.android.com.bookmytractor.data.model.BookMachine

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SuccessResponse {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

}
