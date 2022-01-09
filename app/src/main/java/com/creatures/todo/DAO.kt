package com.creatures.todo

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DAO {
    @Insert
    suspend fun insertTask(entity: Entity)

    @Insert
    fun insert_Task(entity: Entity)

    @Update
    suspend fun updateTask(entity: Entity)

    @Delete
    suspend fun deleteTask(entity: Entity)

    @Query("Delete from to_do")
    suspend fun deleteAll()

    @Query("Select * from to_do")
    suspend fun getTasks():List<CardInfo>

    @Query("Select * from to_do")
    fun newgetTasks(): LiveData<List<CardInfo>>

    @Query("SELECT * FROM to_do")
    fun allStudents(): LiveData<List<Entity>>


}