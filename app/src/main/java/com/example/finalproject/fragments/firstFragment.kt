package com.example.finalproject.fragments

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.finalproject.Communicator
import com.example.finalproject.Login
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [firstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class firstFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var communicator: Communicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("Log Out!")
                builder.setMessage("Do you want to log out?")

                builder.setPositiveButton("Yes") { dialog, which ->
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(activity, Login::class.java)
                    Toast.makeText(activity, "Logged Out Successfully", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    activity!!.finish()
                }

                builder.setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                val alertDialog = builder.create()
                alertDialog.show()
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
//        return inflater.inflate(R.layout.fragment_first, container, false)
        val view: View = inflater.inflate(R.layout.fragment_first, container, false)

        communicator = activity as Communicator

        val user = Firebase.auth.currentUser

        var enrollSelected = false
        var regSelected = false
        var gradesSelected = false
        var transcriptSelected = false
        val selectedArraylist = ArrayList<String>()

        view.welcomeView.text = "Welcome, " + user!!.email.toString()

        //Enroll
        view.btnenrollSubjects.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            if (!enrollSelected) {
                builder.setMessage("Are you sure you want to queue for enrollment?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->

                        enrollSelected = true
                        selectedArraylist.add(Typography.bullet.toString() + " Enrollment")
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            } else {
                builder.setMessage("Don't queue for enrollment?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        btnenrollSubjects.setBackgroundColor(Color.parseColor("#6200EE"))
                        enrollSelected = false
                        selectedArraylist.removeAt(selectedArraylist.indexOf("Enrollment"))
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }
        //RegForm
        view.btnRegForm.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            if (!regSelected) {
                builder.setMessage("Are you sure you want to queue for Registration form?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        btnRegForm.setBackgroundColor(Color.BLUE)
                        regSelected = true
                        selectedArraylist.add(Typography.bullet.toString() + " Registration Form")
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            } else {
                builder.setMessage("Don't queue for the Registration Form?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        btnRegForm.setBackgroundColor(Color.parseColor("#6200EE"))
                        regSelected = false
                        selectedArraylist.removeAt(selectedArraylist.indexOf("Registration Form"))
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }

        //Grades
        view.btnCopyGrades.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            if (!gradesSelected) {
                builder.setMessage("Are you sure you want to queue for Copy of your grades?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        btnCopyGrades.setBackgroundColor(Color.BLUE)
                        gradesSelected = true
                        selectedArraylist.add(Typography.bullet.toString() + " Copy of Grades")
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            } else {
                builder.setMessage("Don't queue for the Copy of grades?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        btnCopyGrades.setBackgroundColor(Color.parseColor("#6200EE"))
                        gradesSelected = false
                        selectedArraylist.removeAt(selectedArraylist.indexOf("Copy of Grades"))
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }

        //Transcript
        view.btnTranscript.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            if (!transcriptSelected) {
                builder.setMessage("Are you sure you want to queue for Transcript?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        btnTranscript.setBackgroundColor(Color.BLUE)
                        transcriptSelected = true
                        selectedArraylist.add(Typography.bullet.toString() + " Transcript")
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            } else {
                builder.setMessage("Don't queue for transcript?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        btnTranscript.setBackgroundColor(Color.parseColor("#6200EE"))
                        transcriptSelected = false
                        selectedArraylist.removeAt(selectedArraylist.indexOf("Transcript"))
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }

        //Queue up
        view.btnQueue.setOnClickListener {
            if (enrollSelected || regSelected || gradesSelected || transcriptSelected) {
                val builder = AlertDialog.Builder(activity)
                builder.setMessage("Do you want to queue now?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        communicator.passAtoB(selectedArraylist)
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            } else {
                Toast.makeText(activity, "Select something to queue up first", Toast.LENGTH_SHORT).show()
            }
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
         * @return A new instance of fragment firstFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            firstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}