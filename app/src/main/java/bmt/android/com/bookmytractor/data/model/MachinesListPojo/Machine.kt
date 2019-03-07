package bmt.android.com.bookmytractor.data.model.MachinesListPojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Machine {

    @SerializedName("category_id")
    @Expose
    var categoryId: String? = null

    @SerializedName("category_name")
    @Expose
    var categoryName: String? = null

    @SerializedName("crops")
    @Expose
    var crops: List<String>? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("price")
    @Expose
    var price: Long? = null

    @SerializedName("reg_no")
    @Expose
    var regNo: String? = null

    @SerializedName("type_id")
    @Expose
    var typeId: String? = null

    @SerializedName("type_name")
    @Expose
    var typeName: String? = null

}
