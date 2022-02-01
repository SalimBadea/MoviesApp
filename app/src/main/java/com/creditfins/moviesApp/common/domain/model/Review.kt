package com.creditfins.moviesApp.common.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(var author: String, var content: String): Parcelable
