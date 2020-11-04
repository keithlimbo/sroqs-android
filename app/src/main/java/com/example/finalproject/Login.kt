package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

const val EXTRA_MESSAGE = "com.example.finalproject"

class Login : AppCompatActivity() {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    private lateinit var authlisten: FirebaseAuth.AuthStateListener
    // [END declare_auth]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        //get login data
        val getUsername = findViewById<EditText>(R.id.login_username)
        val getPassword = findViewById<EditText>(R.id.login_password)
        val btnLogin = findViewById<Button>(R.id.login_btn)


        btnLogin.setOnClickListener {
            val email = getUsername.text.toString()
            val pass = getPassword.text.toString()

            if (getUsername.text.trim().length < 6 || getPassword.text.trim().length < 6){
                getUsername.error = "Please input a valid Email"
                getPassword.error = "Please input a valid password"
            }
            else {
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            progressBar.visibility = View.VISIBLE
                            Log.d("TAG", "signInWithEmail:success")
//                            val user = auth.currentUser
                            val intent = Intent(this, MainActivity::class.java).apply { putExtra(EXTRA_MESSAGE, "Logged In!") }
                            finish()
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.visibility = View.INVISIBLE
                            Log.w("TAG", "signInWithEmail:failure", task.exception)
                            Toast.makeText(this, "Auth Fail", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }


}