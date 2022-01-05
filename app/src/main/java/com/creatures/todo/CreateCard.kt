package com.creatures.todo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.creatures.todo.R
import kotlinx.android.synthetic.main.activity_create_card.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CreateCard : AppCompatActivity() {
    private lateinit var database: myDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()
        save_button.setOnClickListener {
            if (create_title.text.toString().trim { it <= ' ' }.isNotEmpty()
                && create_priority.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {
                var title = create_title.getText().toString().trim()
                var priority = create_priority.getText().toString().trim()

                var str: String = "High"

                if(priority == "High" || priority == "HIGH")
                {
                    priority = "high"
                    DataObject.setData(title, priority)
                    GlobalScope.launch {
                        database.dao().insertTask(Entity(0, title, priority))

                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else if(priority == "Medium" || priority == "MEDIUM")
                {
                    priority = "medium"
                    DataObject.setData(title, priority)
                    GlobalScope.launch {
                        database.dao().insertTask(Entity(0, title, priority))

                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else if (priority == "Low" || priority == "LOW")
                {
                    priority = "low"
                    DataObject.setData(title, priority)
                    GlobalScope.launch {
                        database.dao().insertTask(Entity(0, title, priority))

                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(applicationContext,"This Word is Not allowed for Priority Use: High Medium Low",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}

