package id.whynot.submission3.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignupResponse {
    @SerializedName("error")
    @Expose
    var error: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}