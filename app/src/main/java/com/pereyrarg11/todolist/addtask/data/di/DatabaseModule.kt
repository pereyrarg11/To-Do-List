package com.pereyrarg11.todolist.addtask.data.di

import android.content.Context
import androidx.room.Room
import com.pereyrarg11.todolist.addtask.data.TaskDao
import com.pereyrarg11.todolist.addtask.data.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providesTaskDao(database: ToDoDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideToDoDatabase(@ApplicationContext context: Context): ToDoDatabase {
        return Room.databaseBuilder(context, ToDoDatabase::class.java, "TaskDatabase").build()
    }
}