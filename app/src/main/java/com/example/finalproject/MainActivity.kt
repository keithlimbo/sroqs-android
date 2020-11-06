package com.example.finalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.fragments.firstFragment
import com.example.finalproject.fragments.secondFragment
import com.example.finalproject.fragments.thirdFragment

class MainActivity : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentA = firstFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment, fragmentA).commit()
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