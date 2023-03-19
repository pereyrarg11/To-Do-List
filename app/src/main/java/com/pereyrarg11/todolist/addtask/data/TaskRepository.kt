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
        taskDao.registerTask(model.toEntity())
    }

    suspend fun update(model: TaskModel) {
        taskDao.updateTask(model.toEntity())
    }

    suspend fun delete(model: TaskModel) {
        taskDao.deleteTask(model.toEntity())
    }
}

fun TaskModel.toEntity(): TaskEntity {
    return TaskEntity(id = this.id, title = this.title, selected = this.selected)
}