package com.pereyrarg11.todolist.addtask.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class TasksViewModel @Inject constructor() : ViewModel() {

    private val _isDialogVisible = MutableLiveData<Boolean>()
    val isDialogVisible: LiveData<Boolean> = _isDialogVisible

    fun closeDialog() {
        _isDialogVisible.value = false
    }

    fun createNewTask(taskTitle: String) {
        Log.d("ToDoList", taskTitle)
        closeDialog()
    }

    fun showDialog() {
        _isDialogVisible.value = true
    }
}