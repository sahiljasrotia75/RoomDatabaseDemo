package com.example.todoapplication

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_todo.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dashboard_toolbar.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddTodoActivity : AppCompatActivity() {
    var todoModelDataBase = ArrayList<TodoData>()
    private lateinit var formatted: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        toolbar.textView25.text = "Add Task"
        if (intent.hasExtra("update")) {
            btn_add_task.text = "Update Task"
            et_title.setText(intent.getStringExtra("title").toString())
            et_desc.setText(intent.getStringExtra("desc").toString())
        }
        setClick()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")
        formatted = current.format(formatter)
    }

    private fun setClick() {
        btn_add_task.setOnClickListener {
            if (et_title.text.isEmpty() && et_desc.text.isEmpty()) {
                Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
            } else if (et_title.text.isEmpty()) {
                Toast.makeText(this, "Title can't be empty", Toast.LENGTH_SHORT).show()
            } else if (et_desc.text.isEmpty()) {
                Toast.makeText(this, "Description can't be empty", Toast.LENGTH_SHORT).show()
            } else if (intent.hasExtra("update")) {
                updateTodo()
                finish()
            } else {
                saveTodo()
                finish()
            }
        }
    }


    fun saveTodo() {

        class SaveTask : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg params: Void?): Void? {
                val db = Constant.getDataBase(this@AddTodoActivity)

                val todoDatabase = TodoData()
                todoDatabase.description = et_desc.text.toString().trim()
                todoDatabase.title = et_title.text.toString().trim()
                todoDatabase.time = formatted
                todoModelDataBase.add(todoDatabase)

                try {
                    for (i in 0 until todoModelDataBase.size) {
                        db!!.daoAccess()!!.insert(todoDatabase)
                        break
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }
//            override fun onPostExecute(result: Void?) {
//                super.onPostExecute(result)
//                finish()
//            }
        }
        SaveTask().execute()
//        setSharedPreference(reminderModelDataBase)
    }


    fun updateTodo() {

        class SaveTask : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg params: Void?): Void? {
                val db = Constant.getDataBase(this@AddTodoActivity)

                val todoDatabase = TodoData()
                todoDatabase.description = et_desc.text.toString().trim()
                todoDatabase.title = et_title.text.toString().trim()
                todoDatabase.time = formatted
                todoModelDataBase.add(todoDatabase)

                try {
                    for (i in 0 until todoModelDataBase.size) {
                        db!!.daoAccess()!!.updateData(
                            et_title.text.toString().trim(),
                            et_desc.text.toString().trim(),
                            intent.getStringExtra("update").toString().trim()
                        )
                        break
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
            }
        }
        SaveTask().execute()
    }
}