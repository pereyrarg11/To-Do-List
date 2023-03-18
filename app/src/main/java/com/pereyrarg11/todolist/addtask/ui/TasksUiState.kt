package com.pereyrarg11.todolist.addtask.ui

import com.pereyrarg11.todolist.addtask.ui.model.TaskModel

sealed interface TasksUiState {
    object Loading : TasksUiState // if you don't need data, then use object
    data class Error(val throwable: Throwable) : TasksUiState
    data class Success(val taskList: List<TaskModel>) : TasksUiState
}