package com.example.kotlinhell

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    public fun onClick(view: View){
        var intent = Intent(this, MainActivity::class.java)
        when (view.id)
        {
            R.id.showFirstLab -> {
                intent = Intent(this, HelloWorld::class.java)
            }
            R.id.showSecondLab -> {
                intent = Intent(this, QuadraticEquation::class.java)
            }
            R.id.showThirdLab -> {
                intent = Intent(this, Calculator::class.java)
            }
        }

        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}