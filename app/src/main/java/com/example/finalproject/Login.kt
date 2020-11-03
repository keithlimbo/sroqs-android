package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
//import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val EXTRA_MESSAGE = "com.example.finalproject"

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Access a Cloud Firestore instance from your Activity
//        val db = Firebase.firestore

//        db.collection("users")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result){
//                    Log.d("TAG", "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("TAGFailed", "Error getting documents.", exception)
//            }

        //get login data
        val getUsername = findViewById<EditText>(R.id.login_username)
        val getPassword = findViewById<EditText>(R.id.login_password)
        val btnLogin = findViewById<Button>(R.id.login_btn)

        btnLogin.setOnClickListener {
            val username = "admin"
            val password = "admin"
            if (getPassword.text.toString() == password && getUsername.text.toString() == username){
                val intent = Intent(this, MainActivity::class.java).apply { putExtra(EXTRA_MESSAGE, "Logged In!") }
                finish()
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Incorrect Username and Password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}