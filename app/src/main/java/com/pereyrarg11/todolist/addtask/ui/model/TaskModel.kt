package com.pereyrarg11.todolist.addtask.ui.model

data class TaskModel(
    val id: Int = System.currentTimeMillis().hashCode(),
    val title: String,
    var selected: Boolean = false,
)
