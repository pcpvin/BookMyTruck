package bmt.android.com.bookmytractor.data.model.UserBookingsPojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Machine {

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("reg_no")
    @Expose
    var regNo: String? = null

}
