package com.example.tasklistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.liveData
import com.example.tasklistapp.database.Task
import com.example.tasklistapp.database.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getDatabase(application).taskDao()

    // Метод для получения всех задач
    fun getAllTasks() = liveData(Dispatchers.IO) {
        emit(taskDao.getAllTasks())  // Извлекаем все задачи из базы данных
    }

    // Метод для добавления новой задачи
    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insertTask(task)  // Добавляем задачу в базу данных
        }
    }

    // Метод для удаления задачи
    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.deleteTask(task)  // Удаляем задачу из базы данных
        }
    }
}
