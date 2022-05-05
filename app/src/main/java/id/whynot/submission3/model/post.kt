package id.whynot.submission3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class post (
    var name : String? = null,
    var description : String? = null,
    var photoUrl : String? = null,
) : Parcelable