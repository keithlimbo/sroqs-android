package com.example.finalproject

import android.app.AlertDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEnroll = findViewById<Button>(R.id.btnenrollSubjects)
        val btnReg = findViewById<Button>(R.id.btnRegForm)
        val btnGrades = findViewById<Button>(R.id.btnCopyGrades)
        val btnTranscript = findViewById<Button>(R.id.btnTranscript)

        var enrollSelected: Boolean = false
        var regSelected: Boolean = false
        var gradesSelected: Boolean = false
        var transcriptSelected: Boolean = false

        //Enrollment
        btnEnroll.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            if(!enrollSelected) {
                builder.setMessage("Are you sure you want to queue for enrollment?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show()
                        btnEnroll.setBackgroundColor(Color.RED)
                        enrollSelected = true
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }else{
                builder.setMessage("Don't queue for enrollment?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show()
                        btnEnroll.setBackgroundColor(Color.parseColor("#6200EE"))
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
        btnReg.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            if(!regSelected) {
                builder.setMessage("Are you sure you want to queue for Registration form?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show()
                        btnReg.setBackgroundColor(Color.RED)
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
                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show()
                        btnReg.setBackgroundColor(Color.parseColor("#6200EE"))
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

        //Copy of grades
        btnGrades.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            if(!gradesSelected) {
                builder.setMessage("Are you sure you want to queue for Copy of your grades?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show()
                        btnGrades.setBackgroundColor(Color.RED)
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
                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show()
                        btnGrades.setBackgroundColor(Color.parseColor("#6200EE"))
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
        btnTranscript.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            if(!transcriptSelected) {
                builder.setMessage("Are you sure you want to queue for Transcript?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show()
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
    }
}