package org.example.multitthreading

import kotlin.concurrent.thread
import kotlin.random.Random

fun main() {

    print("Enter number from 0 to 1_000_000_000: ")
    val number = readln().toInt()
    var win = false

    thread {
        var timer = 0
        while (!win){

            println("${++timer}")
            Thread.sleep(1000)

        }
    }

    thread {
        while (!win){
            val randomNumber = Random.nextInt(0,1_000_000_001)
            if(number == randomNumber){
                print("I win. Your number is: $randomNumber")
                win = true
            }
        }
    }


}