package com.pereyrarg11.todolist.addtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.pereyrarg11.todolist.addtask.ui.model.TaskModel

@Composable
fun TasksScreen(viewModel: TasksViewModel) {
    val isDialogVisible: Boolean by viewModel.isDialogVisible.observeAsState(initial = false)
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<TasksUiState>(
        initialValue = TasksUiState.Loading,
        key1 = lifeCycle,
        key2 = viewModel
    ) {
        lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    when(uiState){
        is TasksUiState.Error -> {}
        TasksUiState.Loading -> {}
        is TasksUiState.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                AddTasksDialog(
                    isVisible = isDialogVisible,
                    onDismiss = {
                        viewModel.closeDialog()
                    },
                    onSubmit = {
                        viewModel.createNewTask(it)
                    })
                FabDialog(modifier = Modifier.align(Alignment.BottomEnd), viewModel)
                TaskColumn(
                    taskList = (uiState as TasksUiState.Success).taskList,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun FabDialog(modifier: Modifier, viewModel: TasksViewModel) {
    FloatingActionButton(
        onClick = {
            viewModel.showDialog()
        },
        modifier = modifier.padding(16.dp)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "add")
    }
}

@Composable
fun TaskColumn(taskList: List<TaskModel>, viewModel: TasksViewModel) {
    LazyColumn {
        items(taskList, key = {
            it.id
        }) { entity ->
            TaskRow(entity = entity, viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskRow(entity: TaskModel, viewModel: TasksViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    viewModel.removeItem(entity)
                })
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 2.dp,
            focusedElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = entity.title, modifier = Modifier.weight(1f))
            Checkbox(
                checked = entity.isSelected,
                onCheckedChange = { viewModel.onTaskSelected(entity) }
            )
        }
    }
}

@Composable
fun AddTasksDialog(isVisible: Boolean, onDismiss: () -> Unit, onSubmit: (String) -> Unit) {
    var taskTitle by rememberSaveable { mutableStateOf("") }

    if (isVisible) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Agrega tu tarea",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = taskTitle,
                    singleLine = true,
                    maxLines = 1,
                    onValueChange = { taskTitle = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {
                        onSubmit(taskTitle)
                        taskTitle = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Guardar")
                }
            }
        }
    }
}