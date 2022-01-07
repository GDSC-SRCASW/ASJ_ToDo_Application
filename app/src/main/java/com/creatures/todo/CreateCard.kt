package com.creatures.todo


import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
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
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: NotificationManager
    lateinit var builder: Notification.Builder
    private val channelId = "12345"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)
        setTitle(R.string.Add_Notes_Details)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()

        create_notes_radio_group


        save_button.setOnClickListener {
            if (create_title.text.toString().trim { it <= ' ' }.isNotEmpty()
                && create_description.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {
                var title = create_title.getText().toString().trim()
                var description = create_description.getText().toString().trim()

                var radioGroup: RadioGroup = findViewById(R.id.create_notes_radio_group)


                val selectedOption: Int = radioGroup!!.checkedRadioButtonId
                radioButton = findViewById(selectedOption)

                //val firstName: String = "Chike"
                var priority: String = radioButton.text.toString().trim();


                if(priority.equals("HIGH",true))
                {
                    priority = "high"
                    DataObject.setData(title, priority, description)
                    GlobalScope.launch {
                        database.dao().insertTask(Entity(0, title, priority, description))

                    }
                    val intent = Intent(this, LauncherActivity::class.java)
                    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        notificationChannel = NotificationChannel(channelId, description, NotificationManager .IMPORTANCE_HIGH)
                        notificationChannel.lightColor = Color.BLUE
                        notificationChannel.enableVibration(true)
                        notificationManager.createNotificationChannel(notificationChannel)
                        builder = Notification.Builder(this, channelId).setContentTitle(title).setContentText(priority.trim().toUpperCase())
                            .setSmallIcon(R.drawable .ic_priority_high)
                            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background)).setContentIntent(pendingIntent).setAutoCancel(true)
                    }
                    notificationManager.notify(12345, builder.build())

                    val intent_i = Intent(this, MainActivity::class.java)
                    startActivity(intent_i)
                    finishAffinity()
                }

                else if(priority.equals("MEDIUM",true))
                {
                    priority = "medium"
                    DataObject.setData(title, priority, description)
                    GlobalScope.launch {
                        database.dao().insertTask(Entity(0, title, priority, description))

                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
                else if(priority.equals("LOW",true))
                {
                    priority = "Low"
                    DataObject.setData(title, priority, description)
                    GlobalScope.launch {
                        database.dao().insertTask(Entity(0, title, priority, description))

                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
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

