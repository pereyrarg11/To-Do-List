package com.pereyrarg11.todolist.addtask.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    fun getAllTask(): Flow<List<TaskEntity>>

    @Insert
    suspend fun insertTask(entity: TaskEntity)

    @Update
    suspend fun updateTask(entity: TaskEntity)

    @Delete
    suspend fun deleteTask(entity: TaskEntity)
}