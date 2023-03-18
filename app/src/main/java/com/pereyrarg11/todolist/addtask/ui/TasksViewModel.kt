package com.pereyrarg11.todolist.addtask.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pereyrarg11.todolist.addtask.domain.AddTaskUseCase
import com.pereyrarg11.todolist.addtask.domain.GetTaskListUseCase
import com.pereyrarg11.todolist.addtask.ui.TasksUiState.*
import com.pereyrarg11.todolist.addtask.ui.model.TaskModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    getTaskListUseCase: GetTaskListUseCase
) : ViewModel() {

    val uiState: StateFlow<TasksUiState> = getTaskListUseCase()
        .map(::Success)
        .catch { Error(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000), // pauses the flow after X millis
            Loading // first state
        )

    private val _isDialogVisible = MutableLiveData<Boolean>()
    val isDialogVisible: LiveData<Boolean> = _isDialogVisible

    private val _taskList = mutableStateListOf<TaskModel>()
    val taskList: List<TaskModel> = _taskList

    fun closeDialog() {
        _isDialogVisible.value = false
    }

    fun createNewTask(taskTitle: String) {
        closeDialog()
        val model = TaskModel(title = taskTitle)
        _taskList.add(model)
        viewModelScope.launch {
            addTaskUseCase(model)
        }
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