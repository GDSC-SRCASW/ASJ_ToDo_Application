package com.creatures.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class NotesViewModel(application: Application): AndroidViewModel(application) {

    private val db: myDatabase = myDatabase.getInstance(application)
    internal val allStudents : LiveData<List<CardInfo>> = db.dao().newgetTasks()

    fun insert(entity: Entity){
        db.dao().insert_Task(entity)
    }

}