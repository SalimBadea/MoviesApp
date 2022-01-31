package com.creditfins.moviesApp.common.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id :Int = 0,
    var title: String = "",
    var poster_path: String = "",
    var release_date: String = "",
    var overview: String = "",
    var vote_average: Double = 0.0,
    var popularity: String = ""
) : Parcelable