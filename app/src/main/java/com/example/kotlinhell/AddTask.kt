package com.example.kotlinhell

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class AddTask : AppCompatActivity() {
    lateinit var database: DatabaseReference
    @RequiresApi(Build.VERSION_CODES.O)
    val dateFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT, Locale.US)
    lateinit var isoDate: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.database = FirebaseDatabase.getInstance(Constants.DATABASE_URL).reference

        bindAddTaskButton()
        bindCalendarDialogToTextInput()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, ToDo::class.java)
                this.startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTask() {
        val editTaskTitle = findViewById<EditText>(R.id.editTaskTitle)
        val editTaskDescription = findViewById<EditText>(R.id.editTaskDescription)
        val editTaskDeadline = findViewById<EditText>(R.id.editTaskDeadline)

        val newTaskTitle = editTaskTitle.text.toString()
        val newTaskDescription = editTaskDescription.text.toString()
        val newTaskDeadline = editTaskDeadline.text.toString()

        if (newTaskTitle == "" || newTaskDeadline == "") {
            val alert = AlertDialog.Builder(this).setPositiveButton("Ok") { d, id->d.cancel() }
            alert.setMessage("Введите обязательные поля").create()
            alert.show()
        }
        else {
            val task = Task.create()
            task.title = newTaskTitle
            task.description = newTaskDescription
            task.deadline = this.isoDate
            task.done = false

            val createdTask = this.database.child(Constants.FIREBASE_ITEM).push()
            task.objectId = createdTask.key
            createdTask.setValue(task)

            val intent = Intent(this, ToDo::class.java)
            this.startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bindAddTaskButton() {
        val clickListener = View.OnClickListener { view ->
            addTask()
        }

        val addTaskButton = findViewById<Button>(R.id.addTaskButton)
        addTaskButton.setOnClickListener(clickListener)
    }

    fun bindCalendarDialogToTextInput() {
        val textInput = findViewById<EditText>(R.id.editTaskDeadline)

        val calendar = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val sdf = SimpleDateFormat(Constants.DATE_FORMAT, Locale.US)
            val isoSdf = SimpleDateFormat(Constants.ISO_FORMAT, Locale.US)
            textInput.setText(sdf.format(calendar.time))
            this.isoDate = isoSdf.format(calendar.time)
        }

        textInput.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}