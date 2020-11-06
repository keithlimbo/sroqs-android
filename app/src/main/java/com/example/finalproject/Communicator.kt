package com.example.finalproject

interface Communicator {
    fun passAtoB(selectedList: ArrayList<String>)
    fun passBtoC(windowNumber: Int, queueNum: Int)
}