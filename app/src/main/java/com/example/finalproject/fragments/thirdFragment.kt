package com.example.finalproject.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val database = Firebase.database.reference.child("queue")
        val user = Firebase.auth.currentUser
        val thirdListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    val data = childSnapshot.key.toString()
                    Log.i("TAG", data)
                    view.btnCancel.setOnClickListener {
                        val builder = AlertDialog.Builder(activity)
                        builder.setMessage("Do you want to cancel?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                val userQueue = User(null, null, null)
                                database.child(data).setValue(userQueue)
                                Navigation.findNavController(view).navigate(R.id.action_thirdFragment_to_firstFragment)
                            }
                            .setNegativeButton("No") { dialog, id ->
                                // Dismiss the dialog
                                dialog.dismiss()
                            }
                        val alert = builder.create()
                        alert.show()
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("ERROR", "Database Error")
            }
        }
        database.orderByChild("email").equalTo(user!!.email).addValueEventListener(thirdListener)


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