package com.pereyrarg11.todolist.addtask.data

import com.pereyrarg11.todolist.addtask.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
) {

    val taskListFlow: Flow<List<TaskModel>> = taskDao.getAllTask().map { items ->
        items.map { entity ->
            TaskModel(
                entity.id,
                entity.title,
                entity.selected
            )
        }
    }

    suspend fun add(model: TaskModel) {
        taskDao.registerTask(
            TaskEntity(
                id = model.id,
                title = model.title,
                selected = model.selected
            )
        )
    }

    suspend fun update(model: TaskModel) {
        taskDao.updateTask(TaskEntity(model.id, model.title, model.selected))
    }
}