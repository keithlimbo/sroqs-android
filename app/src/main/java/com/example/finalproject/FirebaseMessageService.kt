package com.example.finalproject

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage




class FirebaseMessageService : FirebaseMessagingService(){

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("New Token","New Token created: $p0")
    }
}