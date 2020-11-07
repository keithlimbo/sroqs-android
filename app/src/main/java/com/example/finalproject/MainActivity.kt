package com.example.finalproject

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.fragments.firstFragment
import com.example.finalproject.fragments.secondFragment
import com.example.finalproject.fragments.thirdFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = Firebase.database.reference.child("queue")
        val user = Firebase.auth.currentUser
        val parentListener = object : ValueEventListener {
            var data : String? = ""
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var sb = StringBuilder()
                if(dataSnapshot.exists()) {
                    for (childSnapshot in dataSnapshot.children) {
                        data = childSnapshot.key
                        Log.i("TAG", data.toString())
                        var uCollege = childSnapshot.child("college").value
                        var uEmail = childSnapshot.child("email").value
                        var uQueue = childSnapshot.child("onQueue").value
                        var uWindow = childSnapshot.child("windowNumber").value
                        val gUser = getUser()
                        gUser.college = uCollege!!
                        gUser.email = uEmail!!
                        gUser.onQueue = uQueue as Boolean
                        gUser.windowNumber = uWindow!!
                        sb.append("${gUser.onQueue} $uCollege $uEmail $uQueue $uWindow")
                        Log.d("String", sb.toString())
                        Log.d("onQueue", gUser.onQueue.toString())
                        if (gUser.onQueue) {
                            Log.d("onQueue", "tur")
                            val fragmentC = thirdFragment()
                            progressBar.visibility = View.GONE
                            supportFragmentManager.beginTransaction().replace(R.id.fragment, fragmentC).commit()
                        }
                    }
                }
                else{
                    Log.d("onQueue", "fas")
                    val fragmentA = firstFragment()
                    progressBar.visibility = View.GONE
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, fragmentA).commit()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("ERROR", "Database Error")
            }
        }
        // Get Parent
        database.orderByChild("email").equalTo(user!!.email).addListenerForSingleValueEvent(parentListener)

    }

    override fun passAtoB(selectedList: ArrayList<String>) {
        val bundle = Bundle()
        bundle.putStringArrayList("selectedCollege", selectedList)

        val transaction =  this.supportFragmentManager.beginTransaction()
        val fragmentB = secondFragment()
        fragmentB.arguments = bundle

        transaction.replace(R.id.fragment, fragmentB)
        transaction.commit()
    }

    override fun passBtoC(windowNumber: Int, queueNum: Int) {
        val bundle = Bundle()
        bundle.putInt("winNum", windowNumber)
        bundle.putInt("queNum", queueNum)

        val transaction =  this.supportFragmentManager.beginTransaction()
        val fragmentC = thirdFragment()
        fragmentC.arguments = bundle

        transaction.replace(R.id.fragment, fragmentC)
        transaction.commit()
    }

    override fun backCtoA(data: String) {
        val database = Firebase.database.reference.child("queue")
        val transaction =  this.supportFragmentManager.beginTransaction()
        val fragmentA = firstFragment()
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to cancel?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                val userQueue = User(null, null, null, 0)
                database.child(data).setValue(userQueue)
                transaction.replace(R.id.fragment, fragmentA)
                transaction.commit()
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    fun goToA(){
        val transaction =  this.supportFragmentManager.beginTransaction()
        val fragmentA = firstFragment()
        transaction.replace(R.id.fragment, fragmentA)
        transaction.commit()
    }

    fun goToC(){
        val transaction =  this.supportFragmentManager.beginTransaction()
        val fragmentC = thirdFragment()
        transaction.replace(R.id.fragment, fragmentC)
        transaction.commit()
    }
}