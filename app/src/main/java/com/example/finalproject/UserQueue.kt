package com.example.finalproject

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var tokenID: String? = "",
    var email: String? = "",
    var college: String? = "",
    var onQueue: Boolean? = false,
    var windowNumber: Int?
)
