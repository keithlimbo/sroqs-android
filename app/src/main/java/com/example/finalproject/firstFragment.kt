package com.example.finalproject

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [firstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class firstFragment : Fragment() {
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
//        return inflater.inflate(R.layout.fragment_first, container, false)
        val view: View = inflater!!.inflate(R.layout.fragment_first, container, false)

        var enrollSelected: Boolean = false
        var regSelected: Boolean = false
        var gradesSelected: Boolean = false
        var transcriptSelected: Boolean = false

        //Enroll
        view.btnenrollSubjects.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            if (!enrollSelected) {
                builder.setMessage("Are you sure you want to queue for enrollment?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        Toast.makeText(activity, "Queued Up!", Toast.LENGTH_SHORT).show()
                        btnenrollSubjects.setBackgroundColor(Color.RED)
                        enrollSelected = true
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
            if(!regSelected) {
                builder.setMessage("Are you sure you want to queue for Registration form?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        Toast.makeText(activity, "yes", Toast.LENGTH_SHORT).show()
                        btnRegForm.setBackgroundColor(Color.RED)
                        regSelected = true
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }else{
                builder.setMessage("Don't queue for the Registration Form?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        Toast.makeText(activity, "yes", Toast.LENGTH_SHORT).show()
                        btnRegForm.setBackgroundColor(Color.parseColor("#6200EE"))
                        regSelected = false
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
            if(!gradesSelected) {
                builder.setMessage("Are you sure you want to queue for Copy of your grades?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        Toast.makeText(activity, "yes", Toast.LENGTH_SHORT).show()
                        btnCopyGrades.setBackgroundColor(Color.RED)
                        gradesSelected = true
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }else{
                builder.setMessage("Don't queue for the Copy of grades?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        Toast.makeText(activity, "yes", Toast.LENGTH_SHORT).show()
                        btnCopyGrades.setBackgroundColor(Color.parseColor("#6200EE"))
                        gradesSelected = false
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
            if(!transcriptSelected) {
                builder.setMessage("Are you sure you want to queue for Transcript?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        Toast.makeText(activity, "yes", Toast.LENGTH_SHORT).show()
                        btnTranscript.setBackgroundColor(Color.RED)
                        transcriptSelected = true
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }else{
                builder.setMessage("Don't queue for transcript?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        Toast.makeText(activity, "yes", Toast.LENGTH_SHORT).show()
                        btnTranscript.setBackgroundColor(Color.parseColor("#6200EE"))
                        transcriptSelected = false
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
            if (enrollSelected || regSelected || gradesSelected|| transcriptSelected){
                //Go to second page
                Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment)
            }else{
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
        // TODO: Rename and change types and number of parameters
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