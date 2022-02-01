package com.creditfins.moviesApp.common.data.rest.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddFavoriteRequest(
    var media_type: String = "",
    var media_id: Int,
    var favorite: Boolean,
    var session_id: String = ""
) : Parcelable