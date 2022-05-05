package id.whynot.submission3.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class loginResponse {
    @SerializedName("loginResult")
    @Expose
    var loginResult: User? = null
    class User{
        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("token")
        @Expose
        var token: String? = null
    }
}
