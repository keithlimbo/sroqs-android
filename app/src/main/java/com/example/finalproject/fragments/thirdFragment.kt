package com.example.finalproject.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.finalproject.Communicator
import com.example.finalproject.MainActivity
import com.example.finalproject.R
import com.example.finalproject.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_third.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [thirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class thirdFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var communicator: Communicator
    var qNumber: Int? = null
    var windowNum: Int? = null
    val database = Firebase.database.reference.child("queue")
    val user = Firebase.auth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                communicator = activity as Communicator
                val thirdListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (childSnapshot in dataSnapshot.children) {
                            val data = childSnapshot.key.toString()
                            Log.i("TAG", data)
                            communicator.backCtoA(data)
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.d("ERROR", "Database Error")
                    }
                }
                database.orderByChild("email").equalTo(user!!.email).addListenerForSingleValueEvent(thirdListener)
                //End Cancel

            }
        })
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_third, container, false)
        windowNum = arguments?.getInt("winNum")
        qNumber = arguments?.getInt("queNum")
        val shared = activity?.getSharedPreferences("SHARED PREF", Context.MODE_PRIVATE)
        if(windowNum != null || qNumber != null) {
            val editor: SharedPreferences.Editor = shared!!.edit()
            editor.putString("QUEUE NUMBER", qNumber.toString())
            editor.putString("WINDOW NUMBER", windowNum.toString())
            editor.apply()
        }

        val qNum = shared!!.getString("QUEUE NUMBER", "")
        val wNum = shared.getString("WINDOW NUMBER", "")
        view.queueNumber.text = qNum
        view.queueNumber2.text = wNum

        Log.d("Nums", qNumber.toString() + " " + windowNum.toString())
        //Cancel
        communicator = activity as Communicator

        view.btnCancel.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setMessage("Do you want to cancel?")
                .setCancelable(false)

                .setPositiveButton("Yes") { dialog, id ->
                    val thirdListener = object : ValueEventListener {
                        var data:String? = ""
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            for (childSnapshot in dataSnapshot.children) {
                                data = childSnapshot.key.toString()
                            }
                            val userQueue = User(null, null, null, 0)
                            database.child(data.toString()).setValue(userQueue)
                        }
                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.d("ERROR", "Database Error")
                        }

                    }
                    database.orderByChild("email").equalTo(user!!.email).addListenerForSingleValueEvent(thirdListener)
                    (activity as MainActivity?)?.goToA()
                }

                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
            //End Cancel
        }


        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment thirdFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            thirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}