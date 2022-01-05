package com.creatures.todo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.room.Room
import com.creatures.todo.R
import kotlinx.android.synthetic.main.activity_create_card.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CreateCard : AppCompatActivity() {

    private lateinit var database: myDatabase
    lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()

        create_notes_radio_group


        save_button.setOnClickListener {
            if (create_title.text.toString().trim { it <= ' ' }.isNotEmpty()
                && create_priority.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {
                var title = create_title.getText().toString().trim()
                //var priority = create_priority.getText().toString().trim()

                var radioGroup: RadioGroup = findViewById(R.id.create_notes_radio_group)


                val selectedOption: Int = radioGroup!!.checkedRadioButtonId
                radioButton = findViewById(selectedOption)

                //val firstName: String = "Chike"
                var priority: String = radioButton.text.toString().trim();


                if(priority.equals("HIGH",true))
                {
                    priority = "high"
                    DataObject.setData(title, priority)
                    GlobalScope.launch {
                        database.dao().insertTask(Entity(0, title, priority))

                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                else if(priority.equals("MEDIUM",true))
                {
                    priority = "medium"
                    DataObject.setData(title, priority)
                    GlobalScope.launch {
                        database.dao().insertTask(Entity(0, title, priority))

                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else if(priority.equals("LOW",true))
                {
                    priority = "Low"
                    DataObject.setData(title, priority)
                    GlobalScope.launch {
                        database.dao().insertTask(Entity(0, title, priority))

                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(applicationContext,"This Word is Not allowed for Priority \nUse: High Medium Low",Toast.LENGTH_SHORT).show()
                }

            }
            else
            {
                Toast.makeText(applicationContext,"Enter the Empty Fields they are Important",Toast.LENGTH_SHORT).show()
            }
        }
    }
}

