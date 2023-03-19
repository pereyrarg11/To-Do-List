package com.pereyrarg11.todolist.addtask.domain

import com.pereyrarg11.todolist.addtask.data.TaskRepository
import com.pereyrarg11.todolist.addtask.ui.model.TaskModel
import javax.inject.Inject

class InsertTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(model: TaskModel) {
        repository.insert(model)
    }
}