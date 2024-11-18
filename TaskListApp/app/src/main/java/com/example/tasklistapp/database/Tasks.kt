package com.example.tasklistapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")  // Название таблицы в базе данных
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // ID задачи, автоинкремент
    val title: String,  // Название задачи
    val description: String // Описание задачи
)
