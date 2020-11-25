package com.example.finalproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.time.LocalTime
import kotlin.properties.Delegates

class Login : AppCompatActivity() {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Set Notif channel
        createnotifChennel()
        startNetworkCallback()
        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]
        //get login data
        val getUsername = findViewById<EditText>(R.id.login_username)
        val getPassword = findViewById<EditText>(R.id.login_password)
        val btnLogin = findViewById<Button>(R.id.login_btn)

        val localtime = LocalTime.now().hour

        if(localtime in 8..15) {
            if (auth.currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                btnLogin.setOnClickListener {
                    val email = getUsername.text.toString()
                    val pass = getPassword.text.toString()

                    if (getUsername.text.trim().length < 6 || getPassword.text.trim().length < 6) {
                        getUsername.error = "Please input a valid Email"
                        getPassword.error = "Please input a valid password"
                    } else {
                        auth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithEmail:success")
                                    /* Create an Intent that will start the Menu-Activity. */
                                    Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                                    Toast.makeText(this, "Login Fail", Toast.LENGTH_SHORT).show()

                                }
                            }
                    }
                }
            }
        }
        else{
            Toast.makeText(this, "Queue is Closed for now", Toast.LENGTH_SHORT).show()
            if (Build.VERSION.SDK_INT >= 21) {
                // If yes, run the fancy new function to end the app and
                //  remove it from the task list.
                finishAndRemoveTask()
            } else {
                // If not, then just end the app without removing it from
                //  the task list.
                finish()
            }
        }
    }

    object Variables {
        var isNetworkConnected: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
            Log.i("Network connectivity", "$newValue")
        }
    }

    private fun startNetworkCallback() {
        val cm: ConnectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE)    as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()
        cm.registerNetworkCallback(
            builder.build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    Variables.isNetworkConnected = true
                }

                override fun onLost(network: Network) {
                    Variables.isNetworkConnected = false
                    Toast.makeText(this@Login, "Connection Lost", Toast.LENGTH_SHORT).show()
                    if (Build.VERSION.SDK_INT >= 21) {
                        // If yes, run the fancy new function to end the app and
                        //  remove it from the task list.
                        finishAndRemoveTask()
                    } else {
                        // If not, then just end the app without removing it from
                        //  the task list.
                        finish()
                    }
                }

            })
    }
    private fun createnotifChennel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(getString(R.string.CHANNEL_ID), name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}