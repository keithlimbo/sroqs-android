package com.example.finalproject.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.finalproject.Communicator
import com.example.finalproject.MainActivity
import com.example.finalproject.R
import com.example.finalproject.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_second.view.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [secondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class secondFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var communicator: Communicator
    var selectedCollegeFromA: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = MaterialAlertDialogBuilder(activity!!,R.style.AlertDialogCustom)
                builder.setMessage("Do you want to cancel?")
                    .setCancelable(false)
                    .setPositiveButton("Yes"){dialog, id ->
                        (activity as MainActivity).goToA()
                    }
                    .setNegativeButton("No"){dialog, id ->
                        dialog.dismiss()
                    }

                val alert = builder.create()
                alert.show()
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
        val view: View = inflater.inflate(R.layout.fragment_second, container, false)
        val builder = MaterialAlertDialogBuilder(activity!!,R.style.AlertDialogCustom)
        communicator = activity as Communicator

        val database = Firebase.database.reference.child("queue")
        val user = Firebase.auth.currentUser
        var maxId = 0
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
                builder.setMessage("Do you have your requirements now?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        var num = 0
                        if(selectedCollege == "College of Engineering, Architecture and Fine Arts" || selectedCollege == "College of Arts and Sciences"){
                            num = 1
                        }
                        if(selectedCollege == "College of Teacher Education" || selectedCollege == "College of Informatics and Computing Sciences"){
                            num = 2
                        }
                        if(selectedCollege == "College of Nursing and Allied Health Sciences" || selectedCollege == "College of Industrial Technology"){
                            num = 3
                        }
                        if(selectedCollege == "College of Accountancy, Business, Economics, and International Hospitality Management" || selectedCollege == "College of Law"){
                            num = 4
                        }
//                        communicator.passBtoC()
                        val prefs = activity!!.getSharedPreferences("SHARED PREF", Context.MODE_PRIVATE)
                        val userQueue = User(prefs.getString("USER TOKEN", "EMPTY"),user!!.email.toString(), selectedCollege, true, num)
                        database.child((maxId + 1).toString()).setValue(userQueue)
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                .show()
            }
        }

        //Cancel
        view.btnCancel.setOnClickListener {
            builder.setMessage("Do you want to go back?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    (activity as MainActivity?)?.goToA()
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