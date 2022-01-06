package com.creatures.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.creatures.todo.R
import kotlinx.android.synthetic.main.activity_create_card.*
import kotlinx.android.synthetic.main.activity_update_card.*
import kotlinx.android.synthetic.main.activity_update_card.create_description
import kotlinx.android.synthetic.main.activity_update_card.create_title
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateCard : AppCompatActivity() {

    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_card)
        setTitle(R.string.Update_notes)


        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val title = DataObject.getData(pos).title
            val priority = DataObject.getData(pos).priority
            val description = DataObject.getData(pos).description

            create_title.setText(title)
            create_priority.setText(priority)
            create_description.setText(description)

            delete_button.setOnClickListener {
                DataObject.deleteData(pos)
                GlobalScope.launch {
                    database.dao().deleteTask(
                        Entity(
                            pos + 1,
                            create_title.text.toString(),
                            create_priority.text.toString(),
                            create_description.text.toString()
                        )
                    )
                }
                myIntent()
            }

            delete_button_2.setOnClickListener {
                DataObject.deleteData(pos)
                GlobalScope.launch {
                    database.dao().deleteTask(
                        Entity(
                            pos + 1,
                            create_title.text.toString(),
                            create_priority.text.toString(),
                            create_description.text.toString()
                        )
                    )
                }
                myIntent()
            }

            update_button.setOnClickListener {
                DataObject.updateData(
                    pos,
                    create_title.text.toString(),
                    create_priority.text.toString(),
                    create_description.text.toString()

                )
                GlobalScope.launch {
                    database.dao().updateTask(
                        Entity(
                            pos + 1,
                            create_title.text.toString(),
                            create_priority.text.toString(),
                            create_description.text.toString()
                        )
                    )
                }
                myIntent()
            }

        }
    }

    fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}