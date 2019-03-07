package bmt.android.com.bookmytractor.data.model.CropListPojo

import java.util.ArrayList

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CropListResponse {

    @SerializedName("crops")
    @Expose
    private var mCrops: ArrayList<String>? = null

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    var crops: ArrayList<String>?
        get() = mCrops
        set(crops) {
            mCrops = crops
        }

}
