package com.refactory.android.todoapp

import androidx.lifecycle.ViewModel
import com.refactory.android.todoapp.data.Repository
import com.refactory.android.todoapp.data.local.entity.TaskEntity

class MainViewModel(private val repository: Repository): ViewModel()  {
    fun insertTask(taskEntity: TaskEntity) = repository.insertTask(taskEntity)
    fun getAllTask(): List<TaskEntity> = repository.getAllTask()
    fun getTaskToday(date: String): List<TaskEntity> = repository.getTaskToday(date)
}
