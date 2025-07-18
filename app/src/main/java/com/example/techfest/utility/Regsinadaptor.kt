package com.example.techfest.utility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.techfest.R
import kotlin.collections.addAll
import kotlin.text.clear

class Regsinadaptor(
private val eventNames: MutableList<String>,
private val onItemClicked: (String) -> Unit,
private val onItemLongPressed: (eventName: String, position: Int) -> Unit
) : RecyclerView.Adapter<Regsinadaptor.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventNameTextView: TextView = itemView.findViewById(R.id.regtitle)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.regsinitem, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: EventViewHolder,
        position: Int
    ) {
        val eventName = eventNames[position]
        holder.eventNameTextView.text = eventName

        // Click listeners are set on the entire item view as before
        holder.itemView.setOnClickListener {
            onItemClicked(eventName)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongPressed(eventName, position)
            true // Consume the long click
        }
    }

    override fun getItemCount(): Int = eventNames.size
    fun removeItem(position: Int) {
        eventNames.removeAt(position)
        notifyItemRemoved(position)
    }
    fun updateData(newEvents: List<String>) {
        eventNames.clear()
        eventNames.addAll(newEvents)
        notifyDataSetChanged()
    }



}