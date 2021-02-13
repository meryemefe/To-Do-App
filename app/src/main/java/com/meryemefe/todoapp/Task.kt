package com.meryemefe.todoapp

import java.io.Serializable

data class Task(var task_id:Int, var task_name:String, var task_deadline:String, var task_priority:String) : Serializable {
}