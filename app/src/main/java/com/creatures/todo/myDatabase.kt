package com.creatures.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Entity::class],version=1)
abstract class myDatabase : RoomDatabase() {
    abstract fun dao(): DAO

    companion object {
        private var INSTANCE: myDatabase? = null
        fun getInstance(context: Context): myDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    myDatabase::class.java,
                    "roomdb"
                )
                    .build()
            }
            return INSTANCE as myDatabase
        }

    }
}