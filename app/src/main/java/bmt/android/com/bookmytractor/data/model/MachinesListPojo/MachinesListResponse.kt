package bmt.android.com.bookmytractor.data.model.MachinesListPojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MachinesListResponse
{
    @SerializedName("crop")
    @Expose
    var crop: String? = null

    @SerializedName("machines")
    @Expose
    var machines: List<Machine>? = null

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

}
