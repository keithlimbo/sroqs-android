package com.example.finalproject

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessageService : FirebaseMessagingService(){

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        val prefs = this.getSharedPreferences("SHARED PREF", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs!!.edit()
        editor.putString("USER TOKEN", p0)
        editor.apply()

        Log.d("New Token","New Token created: "+ prefs.getString("USER TOKEN", "EMPTY"))
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val TAGNOTIFICATION = "Notification"
        val builder = NotificationCompat.Builder(this, getString(R.string.CHANNEL_ID))
            .setSmallIcon(R.drawable.flat)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("You're next in queue! Please proceed to the office.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        Log.d(TAGNOTIFICATION, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAGNOTIFICATION, "Message data payload: ${remoteMessage.data}")
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAGNOTIFICATION, "Message Notification Body: ${it.body}")
            with(NotificationManagerCompat.from(this)){
                notify(1,builder.build())
            }
        }

    }
}