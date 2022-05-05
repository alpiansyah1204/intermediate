package id.whynot.submission3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class modelUserPreferences (
    var name : String? = null,
    var token : String? = null,
) : Parcelable