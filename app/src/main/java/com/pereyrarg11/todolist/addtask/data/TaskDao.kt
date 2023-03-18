package com.pereyrarg11.todolist.addtask.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    fun getAllTask(): Flow<List<TaskEntity>>

    @Insert
    suspend fun registerTask(entity: TaskEntity)
}