package com.example.tasklistapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface TaskDao {

    // Метод для добавления задачи в базу данных
    @Insert
    suspend fun insertTask(task: Task)

    // Метод для получения всех задач из базы данных
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    // Метод для удаления задачи по объекту Task
    @Delete
    suspend fun deleteTask(task: Task)
}
