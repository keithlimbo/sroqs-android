package com.example.finalproject

import android.text.Editable
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var sr_code: String = "",
    var password: String = ""
)