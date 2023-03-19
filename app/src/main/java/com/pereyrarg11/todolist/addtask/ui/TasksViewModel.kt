package com.pereyrarg11.todolist.addtask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pereyrarg11.todolist.addtask.domain.InsertTaskUseCase
import com.pereyrarg11.todolist.addtask.domain.DeleteTaskUseCase
import com.pereyrarg11.todolist.addtask.domain.GetTaskListUseCase
import com.pereyrarg11.todolist.addtask.domain.UpdateTaskUseCase
import com.pereyrarg11.todolist.addtask.ui.TasksUiState.*
import com.pereyrarg11.todolist.addtask.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val insertTaskUseCase: InsertTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTaskListUseCase: GetTaskListUseCase
) : ViewModel() {

    val uiState: StateFlow<TasksUiState> = getTaskListUseCase()
        .map(::Success)
        .catch { Error(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000), // pauses the flow after X millis
            Loading // first state
        )

    private val _isDialogVisible = MutableLiveData<Boolean>()
    val isDialogVisible: LiveData<Boolean> = _isDialogVisible

    fun closeDialog() {
        _isDialogVisible.value = false
    }

    fun createNewTask(taskTitle: String) {
        closeDialog()
        viewModelScope.launch {
            insertTaskUseCase(TaskModel(title = taskTitle))
        }
    }

    fun showDialog() {
        _isDialogVisible.value = true
    }

    fun onTaskSelected(model: TaskModel) {
        viewModelScope.launch {
            updateTaskUseCase(model.copy(isSelected = !model.isSelected))
        }
    }

    fun removeItem(model: TaskModel) {
        viewModelScope.launch {
            deleteTaskUseCase(model)
        }
    }
}