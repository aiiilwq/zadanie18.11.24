package com.example.tasklistapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)  // Указываем сущности и версию базы данных
abstract class TaskDatabase : RoomDatabase() {

    // Абстрактный метод для получения доступа к DAO
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        // Метод для получения единственного экземпляра базы данных (Singleton)
        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"  // Имя файла базы данных
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
