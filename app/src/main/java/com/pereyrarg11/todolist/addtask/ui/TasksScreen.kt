package com.pereyrarg11.todolist.addtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun TasksScreen(viewModel: TasksViewModel) {
    val isDialogVisible: Boolean by viewModel.isDialogVisible.observeAsState(initial = false)

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
                    onClick = { onSubmit(taskTitle) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Guardar")
                }
            }
        }
    }
}