package com.meryemefe.todoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_task_details.*

class TaskDetailsActivity : AppCompatActivity() {

    private lateinit var task: Task
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        // Set title of toolbar
        toolbarTaskDetailsActivity.title = resources.getString(R.string.task_details_top_name)
        setSupportActionBar(toolbarTaskDetailsActivity)

        // Initialize DatabaseHelper object
        dbHelper = DatabaseHelper(this@TaskDetailsActivity)

        // Get the task object
        task = intent.getSerializableExtra("task_object") as Task

        // Get spinner values to find the position of selected priority option
        val spinnerValues = resources.getStringArray(R.array.priority_options)

        // Set the EditText areas and spinner value to current properties of the task
        editTextTaskNameUpdate.setText(task.task_name)
        editTextDeadlineUpdate.setText(task.task_deadline)
        spinnerPriorityUpdate.setSelection( spinnerValues.indexOf(task.task_priority))

        // Set click listener of update button
        buttonTaskUpdate.setOnClickListener {

            val taskName = editTextTaskNameUpdate.text.toString()
            val taskDeadline = editTextDeadlineUpdate.text.toString()
            val taskPriority = spinnerPriorityUpdate.selectedItem.toString()

            updateTask( task.task_id, taskName, taskDeadline, taskPriority)
        }

    }

    /**
     * This method takes properties of Task class and updates the task with given id
     * @param task_id: Int
     * @param task_name: String
     * @param task_deadline: String
     * @param task_priority: String
     */
    private fun updateTask( task_id: Int, task_name: String, task_deadline: String, task_priority: String){

        Toast.makeText(this@TaskDetailsActivity, resources.getString(R.string.task_updated_message), Toast.LENGTH_SHORT).show()
        TaskDAO().updateTask(dbHelper, task_id, task_name, task_deadline, task_priority)
        startActivity( Intent(this@TaskDetailsActivity, MainActivity::class.java))
    }
}