package com.example.finalproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.finalproject.Communicator
import com.example.finalproject.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_third.view.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [thirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class thirdFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var communicator: Communicator
    var qNum: String? = null
    var wNum:String? = null
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
        val checkNumber = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    qNum = childSnapshot.key.toString()
                    wNum = childSnapshot.child("windowNumber").value.toString()
                    view.queueNumber.text = qNum
                    view.queueNumber2.text = wNum
                    Log.i("TAG", "$qNum $wNum")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ERROR", "Database Error")
            }
        }
        database.orderByChild("email").equalTo(user!!.email).addListenerForSingleValueEvent(checkNumber)

        //Cancel
        communicator = activity as Communicator

        view.btnCancel.setOnClickListener {
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
            database.orderByChild("email").equalTo(user.email).addListenerForSingleValueEvent(thirdListener)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(activity, "Transaction Complete", Toast.LENGTH_SHORT).show()
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