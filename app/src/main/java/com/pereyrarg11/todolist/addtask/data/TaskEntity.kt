package com.pereyrarg11.todolist.addtask.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    var selected: Boolean = false,
)
