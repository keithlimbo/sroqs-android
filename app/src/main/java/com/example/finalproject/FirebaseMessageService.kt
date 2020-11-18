package com.example.finalproject

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService




class FirebaseMessageService : FirebaseMessagingService(){

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        val prefs = this.getSharedPreferences("SHARED PREF", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs!!.edit()
        editor.putString("USER TOKEN", p0)
        editor.apply()

        Log.d("New Token","New Token created: "+ prefs.getString("USER TOKEN", "EMPTY"))
    }
}