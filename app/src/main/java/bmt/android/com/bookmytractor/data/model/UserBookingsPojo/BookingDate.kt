package bmt.android.com.bookmytractor.data.model.UserBookingsPojo


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BookingDate {

    @SerializedName("from")
    @Expose
    var from: String? = null


    @SerializedName("to")
    @Expose
    var to: String? = null

}
