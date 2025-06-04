package com.leyvi.practice.androidkotlin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class TodoListViewModel : ViewModel() {
    private val TAG = TodoListViewModel::class.simpleName

    private val _todoItems = MutableLiveData<List<TodoItem>>()
    val todoItems: LiveData<List<TodoItem>> = _todoItems

    fun fetchTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            val todoItemsJsonData = URL("https://jsonplaceholder.typicode.com/todos?userId=1").readText()
            val todoItems = Gson().fromJson<List<TodoItem>>(
                todoItemsJsonData,
                // https://stackoverflow.com/a/12384156
                object : TypeToken<List<TodoItem>>() {}.type)
            Log.d(TAG, "fetchTodos: fetched todo items: $todoItems")
            _todoItems.postValue(todoItems)
        }
    }
}
