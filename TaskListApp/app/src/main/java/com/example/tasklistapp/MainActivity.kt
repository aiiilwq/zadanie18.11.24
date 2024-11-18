package com.example.tasklistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.tasklistapp.screens.TaskScreen
import com.example.tasklistapp.ui.theme.TaskListAppTheme
import com.example.tasklistapp.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskListAppTheme {
                // Подключаем TaskScreen и передаем taskViewModel
                TaskScreen(taskViewModel = taskViewModel)
            }
        }
    }
}
