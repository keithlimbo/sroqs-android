package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
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

        if(auth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else {
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


}