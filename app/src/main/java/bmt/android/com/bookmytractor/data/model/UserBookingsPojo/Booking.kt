package bmt.android.com.bookmytractor.data.model.UserBookingsPojo

import com.google.gson.annotations.SerializedName

class Booking {

    @SerializedName("amount")
    var amount: String? = null
    @SerializedName("booking_contact")
    var bookingContact: BookingContact? = null
    @SerializedName("booking_date")
    var bookingDate: BookingDate? = null
    @SerializedName("booking_id")
    var bookingId: String? = null
    @SerializedName("id")
    var id: Long? = null
    @SerializedName("machine")
    var machine: Machine? = null

}
