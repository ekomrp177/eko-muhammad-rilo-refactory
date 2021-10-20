package com.refactory.android.todoapp.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.refactory.android.todoapp.MainViewModel
import com.refactory.android.todoapp.data.Repository
import com.refactory.android.todoapp.di.Injection
import com.refactory.android.todoapp.ui.task.TaskViewModel

class ViewModelFactory private constructor(private val repository: Repository): ViewModelProvider.NewInstanceFactory(){
    companion object{
        @Volatile
        private var instance : ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TaskViewModel::class.java) -> {
                TaskViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            else -> {
                throw Throwable("Unknown ViewModel Class: "+modelClass.name)
            }
        }
    }
}