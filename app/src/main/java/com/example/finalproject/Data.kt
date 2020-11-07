package com.example.finalproject

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Data(
    var email: String? = "",
    var college: String? = "",
    var onQueue: Boolean? = false,
    var windowNumber: Int?
)