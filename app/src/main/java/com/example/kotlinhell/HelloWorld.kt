package com.example.kotlinhell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class HelloWorld : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_world)

        bindShowWelcomeAlertButton()
    }

    fun bindShowWelcomeAlertButton() {
        val clickListener = View.OnClickListener { view ->
            val editPersonNameInput = findViewById<EditText>(R.id.editPersonName)
            val personName = editPersonNameInput.text.toString()

            val alert = AlertDialog.Builder(this).setPositiveButton("Ok") { d, id->d.cancel() }
            alert.setMessage("Добрый день, $personName.").create()
            alert.show()
        }

        val showWelcomeAlertButton = findViewById<Button>(R.id.showWelcomeAlert)
        showWelcomeAlertButton.setOnClickListener(clickListener)
    }
}