package com.creditfins.moviesApp.common.domain.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var id: Int = 0,
    var name: String = "",
    var phone: String = "",
    var address: String = "",
    @Expose
    @SerializedName("lat")
    var latitude: String = "",
    @Expose
    @SerializedName("lon")
    var longitude: String = "",
    var edit: Boolean = false,
    var delete: Boolean = false
) : Parcelable