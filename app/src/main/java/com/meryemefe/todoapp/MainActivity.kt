package com.meryemefe.todoapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    // Define global variables
    private lateinit var taskList: ArrayList<Task>
    private lateinit var taskCardAdapter: TaskCardAdapter
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Copy database
        copyDatabase()

        // Set title of toolbar
        toolbarMainActivity.title = resources.getString(R.string.app_name)
        setSupportActionBar(toolbarMainActivity)

        // Set recycler view so that we can see the task list
        taskRecyclerView.setHasFixedSize(true)
        taskRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        // Initialize DatabaseHelper object
        dbHelper = DatabaseHelper(this@MainActivity)

        // Get the current tasks in the database
        getAllTasks()

        // Set clickListener so that TaskCreateActivity is started after fab is clicked
        fabAddTask.setOnClickListener {
            startActivity( Intent(this@MainActivity, TaskCreateActivity::class.java))
        }
    }

    /**
     * This method provides to quit from app when clicked back on main page.
     */
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    /**
     * This method creates menu and search bar.
     * @param menu: Menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.toolbar_search_menu, menu)

        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    /**
     * This method determines the action when query on text bar is submitted.
     * @param query: String
     * @return true
     */
    override fun onQueryTextSubmit(query: String): Boolean {
        searchTasks(query)
        return true
    }

    /**
     * This method determines the action when query on text bar is changed.
     * @param newText: String
     * @return true
     */
    override fun onQueryTextChange(newText: String): Boolean {
        searchTasks(newText)
        return true
    }

    /**
     * This method creates DatabaseCopyHelper object and opens the database.
     * @throws IOException -> When database cannot be created or opened
     */
    private fun copyDatabase(){
        val copyHelper = DatabaseCopyHelper(this@MainActivity)

        try {
            copyHelper.createDataBase()
            copyHelper.openDataBase()
        } catch (e: IOException){
            e.printStackTrace()
        }
    }

    /**
     * This method gets all tasks from the database and updates taskList.
     */
    private fun getAllTasks(){

        taskList = TaskDAO().selectAllTasks(dbHelper)
        taskCardAdapter = TaskCardAdapter(this@MainActivity, taskList, dbHelper)
        taskRecyclerView.adapter = taskCardAdapter
    }

    /**
     * This method searches all tasks which contains searchWord from the database and updates taskList.
     * @param searchWord: String
     */
    private fun searchTasks( searchWord: String){

        taskList = TaskDAO().searchTasks(dbHelper, searchWord)
        taskCardAdapter = TaskCardAdapter(this@MainActivity, taskList, dbHelper)
        taskRecyclerView.adapter = taskCardAdapter
    }

}