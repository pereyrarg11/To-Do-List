package com.pereyrarg11.todolist.addtask.domain

import com.pereyrarg11.todolist.addtask.data.TaskRepository
import com.pereyrarg11.todolist.addtask.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskListUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    operator fun invoke(): Flow<List<TaskModel>> = repository.taskListFlow
}