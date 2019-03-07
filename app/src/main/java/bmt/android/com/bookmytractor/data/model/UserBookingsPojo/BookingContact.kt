package bmt.android.com.bookmytractor.data.model.UserBookingsPojo


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class BookingContact {

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

}
