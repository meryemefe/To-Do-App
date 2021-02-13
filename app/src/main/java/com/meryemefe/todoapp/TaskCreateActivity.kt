package com.meryemefe.todoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_task_create.*

class TaskCreateActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_create)

        // Set title of toolbar
        toolbarTaskCreateActivity.title = resources.getString(R.string.create_task_top_name)
        setSupportActionBar(toolbarTaskCreateActivity)

        // Initialize DatabaseHelper object
        dbHelper = DatabaseHelper(this@TaskCreateActivity)

        // Set click listener of buttonCreateTask so that it adds new task to database
        buttonCreateTask.setOnClickListener {

            // Get the data from the form
            val taskName = editTextTaskName.text.toString()
            val taskDeadline = editTextTaskDeadline.text.toString()
            val taskPriority = spinnerPriority.selectedItem.toString()

            createTask( taskName, taskDeadline, taskPriority)
        }
    }

    /**
     * This method takes properties of Task class and adds new task to the database
     * @param task_name: String
     * @param task_deadline: String
     * @param task_priority: String
     */
    private fun createTask(task_name: String, task_deadline: String, task_priority: String){

        Toast.makeText(this@TaskCreateActivity, resources.getString(R.string.task_created_message), Toast.LENGTH_SHORT).show()
        TaskDAO().insertTask(dbHelper, task_name, task_deadline, task_priority)
        startActivity( Intent(this@TaskCreateActivity, MainActivity::class.java))
    }
}