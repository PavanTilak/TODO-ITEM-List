package com.example.todolist.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.api.CheckboxState
import com.example.todolist.model.ToDoListModel

class ToDoListAdapter(private val mToDoListList: MutableList<ToDoListModel>?, private val context: Context, private val checkboxState: CheckboxState)
    : RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todolist_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoListModel = mToDoListList!![position]

        holder.userId.text = "UserId : " + todoListModel.userId
        holder.id.text = "Id : " + todoListModel.id
        holder.title.text = "Title : " + todoListModel.title
        holder.completed.text = "Completed : " + todoListModel.completed

        if(todoListModel.completed) {
            holder.mViewRoot.setBackgroundResource(R.color.green)
        } else {
            holder.mViewRoot.setBackgroundResource(R.color.white)
            holder.title.setPaintFlags(holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
        }

        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = todoListModel.completed
        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            //Pass the data to MainActivity to update the views
            checkboxState.checkboxState(isChecked , position)
        }
    }

    override fun getItemCount(): Int {
        return mToDoListList?.size ?: 0
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val userId: TextView = view.findViewById(R.id.userId)
        val id: TextView = view.findViewById(R.id.id)
        val title: TextView = view.findViewById(R.id.title)
        val completed: TextView = view.findViewById(R.id.completed)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
        val mViewRoot: ConstraintLayout = view.findViewById(R.id.mViewRoot)
    }
}
