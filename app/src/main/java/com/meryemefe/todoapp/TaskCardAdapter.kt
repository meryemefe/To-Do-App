package com.meryemefe.todoapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TaskCardAdapter(var mContext: Context, var taskList: ArrayList<Task>, var dbHelper: DatabaseHelper)
    : RecyclerView.Adapter<TaskCardAdapter.CardDesignHolder>(){

        inner class CardDesignHolder( design: View) : RecyclerView.ViewHolder(design){

            var lineCard: CardView
            var lineText: TextView
            var lineImage: ImageView

            init {
                lineCard = design.findViewById(R.id.line_card)
                lineText = design.findViewById(R.id.line_text)
                lineImage = design.findViewById(R.id.line_image)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {

        val design = LayoutInflater.from(mContext).inflate(R.layout.task_line_design, parent, false)
        return CardDesignHolder(design)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) {

        val task = taskList[position]

        holder.lineText.text = "${task.task_name}\n" +
                                "${mContext.resources.getString(R.string.deadline_text).toUpperCase()}: ${task.task_deadline}\n" +
                                "${mContext.resources.getString(R.string.priority_text).toUpperCase()}: ${task.task_priority}"

        holder.lineImage.setOnClickListener {
            askForDeleteOrder(task)
        }

        holder.lineCard.setOnClickListener {
            val intent = Intent(mContext, TaskDetailsActivity::class.java)
            intent.putExtra("task_object", task)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    /**
     * This method asks user for deleting a task.
     * If user approves, the method deletes the task from the database.
     * @param task: Task
     */
    private fun askForDeleteOrder( task: Task){

        val alert = AlertDialog.Builder(mContext)
        alert.setTitle(mContext.resources.getString(R.string.task_delete_confirmation))
        alert.setPositiveButton(mContext.resources.getString(R.string.positive_answer)){ d, i ->

            // Delete the task from the database
            TaskDAO().deleteTask(dbHelper, task.task_id)

            Toast.makeText(mContext, "'${task.task_name}' ${mContext.resources.getString(R.string.task_deleted_message)}", Toast.LENGTH_SHORT).show()

            // Update the user interface
            taskList = TaskDAO().selectAllTasks(dbHelper)
            notifyDataSetChanged()
        }
        alert.setNegativeButton(mContext.resources.getString(R.string.negative_answer)){ d, i ->
            d.dismiss()
        }
        alert.create().show()

    }
}