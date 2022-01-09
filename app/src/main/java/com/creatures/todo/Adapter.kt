package com.creatures.todo

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.creatures.todo.R
import kotlinx.android.synthetic.main.activity_update_card.*
import kotlinx.android.synthetic.main.view.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Adapter(var data: List<CardInfo>) : RecyclerView.Adapter<Adapter.viewHolder>() {

    lateinit var database: myDatabase

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.title
        var priority = itemView.priority
        var description = itemView.description
        var layout = itemView.mylayout
        var share_img = itemView.image_view_share
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.view, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        when (data[position].priority.toLowerCase()) {
            "high" -> holder.layout.setBackgroundColor(Color.parseColor("#F05454"))
            "medium" -> holder.layout.setBackgroundColor(Color.parseColor("#EDC988"))
            else -> holder.layout.setBackgroundColor(Color.parseColor("#00917C"))
        }

        holder.title.text = data[position].title
        holder.priority.text = data[position].priority
        holder.description.text = data[position].description

        holder.share_img.setOnClickListener{

            val title_1 = DataObject.getData(position).title
            val priority_2 = DataObject.getData(position).priority
            val description_3 = DataObject.getData(position).description

            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"Title:"+title_1+"\n\nDescription:"+description_3+"\n\nPriority:"+priority_2)
            intent.type="text/plain"
            holder.itemView.context.startActivity(Intent.createChooser(intent,"Share To:"))

        }


        val user_notes9: MutableLiveData<List<Entity>> by lazy {
            MutableLiveData<List<Entity>>()
        }


        holder.itemView.setOnClickListener{
            val intent= Intent(holder.itemView.context, UpdateCard::class.java)
            intent.putExtra("id",position)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}