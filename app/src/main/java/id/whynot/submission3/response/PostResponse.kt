package id.whynot.submission3.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import id.whynot.submission3.model.post

class PostResponse {
    @SerializedName("listStory")
    @Expose
    var listStory: List<post>? = null

}