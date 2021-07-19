package com.example.todoapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Canvas
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dashboard_toolbar.view.*

class HomeActivity : AppCompatActivity(), TodoAdapter.setClick {
    private lateinit var mTodoAdapter: TodoAdapter
    var todoModelDataBase = ArrayList<TodoData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toolbar_home.textView25.text = "Home"
        setClick()
        getTask()
        setAdapter()
        deleteReminder()
    }

    private fun setClick() {
        fab_add.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getTask()
    }

    private fun setAdapter() {
        //**************suggested adapter
        val manager = LinearLayoutManager(this)
        rv_todo.layoutManager = manager
        mTodoAdapter = TodoAdapter(this, todoModelDataBase)
        rv_todo.adapter = mTodoAdapter

    }

    fun getTask() {
        class GetTask : AsyncTask<Void, Void, ArrayList<TodoData>>() {
            override fun doInBackground(vararg params: Void?): ArrayList<TodoData>? {

                val db = this@HomeActivity.let {
                    Constant.getDataBase(it)
                }
                try {
                    todoModelDataBase =
                        db!!.daoAccess()!!.getAll() as ArrayList<TodoData>
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return todoModelDataBase
            }

            @SuppressLint("WrongConstant")
            override fun onPostExecute(result: ArrayList<TodoData>) {
                super.onPostExecute(result)
                mTodoAdapter.update(result)
            }
        }
        GetTask().execute()
    }

    private fun deleteReminder() {
        val callback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var position = viewHolder.adapterPosition
                val msg = String()
                val data = todoModelDataBase[position]
                val mDialog = AlertDialog.Builder(this@HomeActivity)
                mDialog.setTitle(getString(R.string.alert))
                mDialog.setMessage(msg)
                mDialog.setPositiveButton(
                    getString(R.string.ok)
                ) { dialog, which ->
                    mTodoAdapter.removeAt(position, data)
                    deleteTaskValues(position, data)

                }

                mDialog.setNegativeButton(
                    getString(R.string.cancel)
                ) { dialog, which ->
                    dialog.cancel()
                    getTask()

                }
                mDialog.show()

                if (todoModelDataBase.size == 0) {
                    Toast.makeText(this@HomeActivity, "No Todo Found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            this@HomeActivity,
                            R.color.colorRed
                        )
                    )
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate()
                super.onChildDraw(
                    c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rv_todo)
    }

    private fun deleteTaskValues(modelData: Int, todoModelDatabase: TodoData) {
        class DeleteTask : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg params: Void?): Void? {
                val db = Constant.getDataBase(this@HomeActivity)
                try {
                    db!!.daoAccess()!!.delete(todoModelDatabase)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }
        }
        DeleteTask().execute()
    }

    override fun setAdapterClick(model: TodoData, position: Int) {
        val intent = Intent(this, AddTodoActivity::class.java)
        intent.putExtra("update", model.time)
        intent.putExtra("title", model.title)
        intent.putExtra("desc", model.description)
        startActivity(intent)

    }
}