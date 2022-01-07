package com.creatures.todo


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.creatures.todo.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


// entity - table
// dao - queries
// database

class MainActivity : AppCompatActivity() {

    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.Home_activity)

        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()

        add.setOnClickListener {
            val intent = Intent(this, CreateCard::class.java)
            startActivity(intent)
        }

        setRecycler()

    }

    fun setRecycler() {
        recycler_view.adapter = Adapter(DataObject.getAllData())
        recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_test , menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item != null)
            when(item.itemId){
                R.id.kotlin -> {
                    DataObject.deleteAll()
                    GlobalScope.launch {
                        database.dao().deleteAll()
                    }
                    setRecycler()
                }
                R.id.share -> {
                    val intent= Intent()
                    intent.action=Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this ToDo application with Recycler View and Room with It. \n\n Checkout Our Application On Github \n\nhttps://github.com/Preetojha08/ASJ_ToDo_Application")
                    intent.type="text/plain"
                    startActivity(Intent.createChooser(intent,"Share To:"))
                }
                R.id.feedback -> {
                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.data = Uri.parse("mailto:") // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, "feedback@gmail.com")
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback")
                    startActivity(intent)

                }
            }

        return true
    }
}