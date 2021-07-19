package com.example.todoapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_adapter.view.*

class TodoAdapter(
    var context: Context,
    var mData: ArrayList<TodoData>
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    var click = context as setClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.todo_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = mData[position]
        holder.itemView.tv_title.text = model.title
        holder.itemView.tv_desc.text = model.description
        holder.itemView.tv_date_time.text = model.time

        holder.itemView.setOnClickListener{
            click.setAdapterClick(model, position)
            notifyDataSetChanged()
        }
    }

    fun update(it: ArrayList<TodoData>) {
        mData = it
        notifyDataSetChanged()
    }

    fun removeAt(position: Int, data: TodoData) {
        mData.removeAt(position)
        notifyItemRemoved(position)
    }

    interface setClick {
        fun setAdapterClick(model: TodoData, position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}