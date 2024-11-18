package com.example.tasklistapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasklistapp.database.Task
import com.example.tasklistapp.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(taskViewModel: TaskViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    val allTasks by taskViewModel.getAllTasks().observeAsState(listOf())
    var tasks by remember { mutableStateOf(allTasks) }

    // Обновляем локальное состояние задач при изменении данных в базе
    LaunchedEffect(allTasks) {
        tasks = allTasks
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFEDE7F6)) // Светлый фон для всего приложения
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Центрируем элементы
        ) {
            // Заголовок приложения с новым фоном
            Text(
                text = "Task List",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold // Жирный шрифт для заголовка
                ),
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            // Поле для ввода названия задачи
            TextField(
                value = taskTitle,
                onValueChange = { taskTitle = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )

            // Поле для ввода описания задачи
            TextField(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )

            // Список задач
            tasks.forEach { task ->
                TaskItem(task = task, onDelete = { deletedTask ->
                    taskViewModel.deleteTask(deletedTask)
                    tasks = tasks.filter { it != deletedTask } // Удаляем задачу из локального состояния
                })
            }
        }

        // Кнопка для добавления новой задачи
        FloatingActionButton(
            onClick = {
                if (taskTitle.isNotEmpty() && taskDescription.isNotEmpty()) {
                    val newTask = Task(title = taskTitle, description = taskDescription)
                    taskViewModel.addTask(newTask)
                    taskTitle = ""
                    taskDescription = ""
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = RoundedCornerShape(50),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Task", tint = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun TaskItem(task: Task, onDelete: (Task) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(10.dp, shape = RoundedCornerShape(12.dp)) // Увеличенная тень для карточки
            .background(Color(0xFF424242)), // Темный фон для карточки
        shape = RoundedCornerShape(12.dp), // Закругленные углы для карточки
        colors = CardDefaults.cardColors(containerColor = Color(0xFF424242)) // Темный цвет фона
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White // Белый цвет для текста в карточке
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                    color = Color(0xFFB0B0B0) // Светло-серый цвет для описания
                )
            }
            IconButton(onClick = { onDelete(task) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskScreen() {
    TaskScreen()
}
