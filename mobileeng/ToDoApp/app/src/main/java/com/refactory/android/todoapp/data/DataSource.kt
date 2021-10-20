package com.refactory.android.todoapp.data

import com.refactory.android.todoapp.data.local.entity.TaskEntity

interface DataSource {
    // local
    fun getAllTask(): List<TaskEntity>
    fun insertTask(taskEntity: TaskEntity)
    fun deleteTask(id: Long)
    fun updateTask(taskEntity: TaskEntity)
    fun getTaskToday(date: String): List<TaskEntity>
}