package com.pereyrarg11.todolist.addtask.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pereyrarg11.todolist.addtask.ui.model.TaskModel
import javax.inject.Inject

class TasksViewModel @Inject constructor() : ViewModel() {

    private val _isDialogVisible = MutableLiveData<Boolean>()
    val isDialogVisible: LiveData<Boolean> = _isDialogVisible

    private val _taskList = mutableStateListOf<TaskModel>()
    val taskList: List<TaskModel> = _taskList

    fun closeDialog() {
        _isDialogVisible.value = false
    }

    fun createNewTask(taskTitle: String) {
        closeDialog()
        _taskList.add(TaskModel(title = taskTitle))
    }

    fun showDialog() {
        _isDialogVisible.value = true
    }

    fun onTaskSelected(entity: TaskModel) {
        val index = _taskList.indexOf(entity)
        _taskList[index] = _taskList[index].let {
            it.copy(selected = !it.selected)
        }
    }

    fun removeItem(entity: TaskModel) {
        val targetTask = _taskList.find { it.id == entity.id }
        _taskList.remove(targetTask)
    }
}