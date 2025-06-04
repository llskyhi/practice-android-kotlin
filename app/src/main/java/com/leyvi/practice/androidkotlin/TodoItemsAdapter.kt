package com.leyvi.practice.androidkotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leyvi.practice.androidkotlin.databinding.RowTodoBinding

class TodoItemsAdapter(todoItems: List<TodoItem>) :
    RecyclerView.Adapter<TodoItemsAdapter.TodoItemViewHolder>() {

    class TodoItemViewHolder(private val binding: RowTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val textTodoTitle by lazy { binding.textTodoTitle }
        val checkboxHasDone by lazy { binding.checkboxHasDone }
    }

    private val todoItems: List<TodoItem> by lazy { ArrayList(todoItems) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val binding = RowTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoItemViewHolder(binding)
    }

    override fun getItemCount(): Int = todoItems.size

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val todoItem = todoItems[position]
        holder.textTodoTitle.text = todoItem.title
        holder.checkboxHasDone.isChecked = todoItem.completed
    }
}