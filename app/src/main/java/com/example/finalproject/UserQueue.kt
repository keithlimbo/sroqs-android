package com.example.finalproject

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var email: String? = "",
    var college: String? = "",
    var onQueue: Boolean? = false
){
    @Exclude
    fun toCancel(): Map<String, Any?> {
        return mapOf(
            "onQueue" to true
        )
    }
}