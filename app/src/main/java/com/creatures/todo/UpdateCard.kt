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
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: NotificationManager
    lateinit var builder: Notification.Builder
    private val channelId = "123456"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_card)
        setTitle(R.string.Update_notes)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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

                val prior: String = create_priority.getText().toString().trim()
                val title: String = create_title.getText().toString().trim()

                if (prior.equals("HIGH",true))
                {
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
                    notificationManager.notify(123456, builder.build())
                }

                myIntent()
            }

        }
    }

    fun myIntent() {
        val intent_i = Intent(this, MainActivity::class.java)
        startActivity(intent_i)
        finishAffinity()
    }
}