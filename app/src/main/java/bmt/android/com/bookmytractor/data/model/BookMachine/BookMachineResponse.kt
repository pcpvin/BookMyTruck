package bmt.android.com.bookmytractor.data.model.BookMachine


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BookMachineResponse {

    @SerializedName("amount")
    @Expose
    var amount: String? = null

    @SerializedName("booking_id")
    @Expose
    var bookingId: String? = null

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

}
