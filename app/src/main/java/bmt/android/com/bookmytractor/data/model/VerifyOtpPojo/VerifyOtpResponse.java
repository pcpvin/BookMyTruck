
package bmt.android.com.bookmytractor.data.model.VerifyOtpPojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@SuppressWarnings("unused")
public class VerifyOtpResponse {

    @SerializedName("access_token")
    @Expose
    private String mAccessToken;
    @SerializedName("success")
    @Expose
    private Boolean mSuccess;

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
