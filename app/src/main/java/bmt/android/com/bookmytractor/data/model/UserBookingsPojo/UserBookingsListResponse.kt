package bmt.android.com.bookmytractor.data.model.UserBookingsPojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class UserBookingsListResponse {

    @SerializedName("bookings")
    @Expose
    var bookings: List<Booking>? = null

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

}
