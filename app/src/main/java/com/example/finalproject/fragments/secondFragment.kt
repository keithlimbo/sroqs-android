package com.example.finalproject.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.finalproject.R
import com.example.finalproject.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.fragment_second.view.*
import java.lang.NullPointerException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [secondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class secondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var selectedCollegeFromA: ArrayList<String>? = null

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
        val view: View = inflater!!.inflate(R.layout.fragment_second, container, false)
        val database = Firebase.database.reference.child("queue")
        val user = Firebase.auth.currentUser
        var maxId: Int = 0
        var selectedToQueue: String? = null
        selectedCollegeFromA = arguments?.getStringArrayList("selectedCollege")

        for(i in selectedCollegeFromA!!){
            if (selectedToQueue == null) {
                selectedToQueue = i + "\n"
            } else {
                selectedToQueue += i + "\n"
            }
        }
        //Print selected
        view.selectTransac.text = selectedToQueue

        //Generate UID
        val secondListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                maxId = (dataSnapshot.childrenCount.toInt())
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(secondListener)

        //Confirm
        view.btnConfirm.setOnClickListener {
            val selectedCollege = view.spinner.selectedItem.toString()
            if (selectedCollege == "--") {
                Toast.makeText(activity, "Please choose your college department", Toast.LENGTH_SHORT).show()
            }else {
                val builder = AlertDialog.Builder(activity)
                builder.setMessage("Do you have your requirements now?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        val userQueue = User(user!!.email.toString(), selectedCollege, true)
                        database.child((maxId + 1).toString()).setValue(userQueue)
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }

        //Cancel
        view.btnCancel.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setMessage("Do you want to go back?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
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
         * @return A new instance of fragment secondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            secondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}