package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.ValueEventListener
//import com.google.firebase.database.ktx.database
//import com.google.firebase.database.ktx.getValue
//import com.google.firebase.ktx.Firebase

const val EXTRA_MESSAGE = "com.example.finalproject"

class Login : AppCompatActivity() {

//    companion object {
//        private const val TAG = "KotlinActivity"
//    }

//    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        val database = Firebase.database

//        fun basicReadWrite() {
//            // [START write_message]
//            // Write a message to the database
//
//            val myRef = database.getReference("message")
//
//            myRef.setValue("Hello, World!")
//            // [END write_message]
//
//            // [START read_message]
//            // Read from the database
//            myRef.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    // This method is called once with the initial value and again
//                    // whenever data at this location is updated.
//                    val value = dataSnapshot.getValue<String>()
//                    Log.d(TAG, "Value is: $value")
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    // Failed to read value
//                    Log.w(TAG, "Failed to read value.", error.toException())
//                }
//            })
//            // [END read_message]
//        }



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

//    private fun writeNewUser(sr_code: String, pass: String) {
//        val user = User(sr_code, pass)
//        database.getReference().child("users").setValue(user)
//    }
}