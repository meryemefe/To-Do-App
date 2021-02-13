package com.meryemefe.todoapp

import android.content.ContentValues

class TaskDAO {

    /**
     * This method gets all tasks from database.
     * @param dbHelper: DatabaseHelper
     * @return ArrayList<Task> -> list of all tasks
     */
    fun selectAllTasks(dbHelper: DatabaseHelper): ArrayList<Task>{

        val db = dbHelper.writableDatabase
        val taskList = ArrayList<Task>()

        val cursor = db.rawQuery("SELECT * FROM task",null)
        while (cursor.moveToNext()){
            val task = Task( cursor.getInt(cursor.getColumnIndex("task_id")),
                             cursor.getString(cursor.getColumnIndex("task_name")),
                             cursor.getString(cursor.getColumnIndex("task_deadline")),
                             cursor.getString(cursor.getColumnIndex("task_priority")))
            taskList.add(task)
        }

        db.close()
        return taskList
    }

    /**
     * This method searches the tasks which contains searchWord.
     * @param dbHelper: DatabaseHelper
     * @param searchWord: String
     * @return ArrayList<Task> -> list of all tasks which contains searchWord
     */
    fun searchTasks( dbHelper: DatabaseHelper, searchWord: String): ArrayList<Task>{

        val db = dbHelper.writableDatabase
        val taskList = ArrayList<Task>()

        val cursor = db.rawQuery("SELECT * FROM task WHERE task_name LIKE '%$searchWord%'",null)
        while (cursor.moveToNext()){
            val task = Task( cursor.getInt(cursor.getColumnIndex("task_id")),
                             cursor.getString(cursor.getColumnIndex("task_name")),
                             cursor.getString(cursor.getColumnIndex("task_deadline")),
                             cursor.getString(cursor.getColumnIndex("task_priority")))
            taskList.add(task)
        }
        db.close()

        return taskList
    }

    /**
     * This method inserts a new task to the database.
     * @param dbHelper: DatabaseHelper
     * @param task_name: String
     * @param task_deadline: String
     * @param task_priority: String
     */
    fun insertTask( dbHelper: DatabaseHelper, task_name: String, task_deadline: String, task_priority: String){
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put("task_name", task_name)
        values.put("task_deadline", task_deadline)
        values.put("task_priority", task_priority)

        db.insertOrThrow("task",null, values)
        db.close()
    }

    /**
     * This method updates the task with given task_id and values.
     * @param dbHelper: DatabaseHelper
     * @param task_id: Int
     * @param task_name: String
     * @param task_deadline: String
     * @param task_priority: String
     */
    fun updateTask( dbHelper: DatabaseHelper, task_id: Int, task_name: String, task_deadline: String, task_priority: String){
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put("task_name", task_name)
        values.put("task_deadline", task_deadline)
        values.put("task_priority", task_priority)

        db.update("task", values,"task_id=?", arrayOf(task_id.toString()))
        db.close()
    }

    /**
     * This method delete the task with the given task_id from the database.
     * @param dbHelper: DatabaseHelper
     * @param task_id: Int
     */
    fun deleteTask( dbHelper: DatabaseHelper, task_id: Int){

        val db = dbHelper.writableDatabase
        db.delete("task","task_id=?", arrayOf(task_id.toString()))
        db.close()
    }

}