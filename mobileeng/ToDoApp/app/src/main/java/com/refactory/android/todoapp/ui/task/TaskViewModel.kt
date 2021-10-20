package com.refactory.android.todoapp.ui.task

import androidx.lifecycle.ViewModel
import com.refactory.android.todoapp.data.Repository
import com.refactory.android.todoapp.data.local.entity.TaskEntity

class TaskViewModel(private val repository: Repository): ViewModel()  {
    fun getAllTask(): List<TaskEntity> = repository.getAllTask()
    fun deleteTask(id: Long) = repository.deleteTask(id)
    fun updateTask(taskEntity: TaskEntity) = repository.updateTask(taskEntity)
    fun getTaskToday(date: String): List<TaskEntity> = repository.getTaskToday(date)
}