package com.meryemefe.todoapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context)
    : SQLiteOpenHelper(context,"toDoApp.sqlite",null,1){

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("CREATE TABLE IF NOT EXISTS \"task\" (\n" +
                "\t\"task_id\"\tINTEGER,\n" +
                "\t\"task_name\"\tTEXT,\n" +
                "\t\"task_deadline\"\tTEXT,\n" +
                "\t\"task_priority\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"task_id\" AUTOINCREMENT)\n" +
                ")")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS task")
        onCreate(db)
    }

}