package com.example.techfest.utility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.techfest.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class EDetailAdapter : RecyclerView.Adapter<EDetailAdapter.EventViewHolder>() {

    private var events = listOf<EventData>()

    fun submitList(newList: List<EventData>) {
        events = newList
        notifyDataSetChanged()
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView = itemView.findViewById(R.id.time)
        val title: TextView = itemView.findViewById(R.id.ename)
        val date: TextView = itemView.findViewById(R.id.date_of_portion)
        val venue: TextView = itemView.findViewById(R.id.venue_of_subevent)
        val line: View = itemView.findViewById(R.id.line)
        val doti:View = itemView.findViewById(R.id.doti)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        val eventTime = event.startTime.toDate()
        val now = Date()
        val isLastEvent = (position == events.lastIndex)
        val endTime = if (!isLastEvent) {
            events[position + 1].startTime.toDate()
        } else {
            null // No end time for the last event
        }


        // Format time and date in IST
        val timeFormatter = SimpleDateFormat("hh:mm a", Locale("en", "IN")).apply {
            timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        }
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale("en", "IN")).apply {
            timeZone = TimeZone.getTimeZone("Asia/Kolkata")
        }

        holder.time.text = timeFormatter.format(eventTime)
        holder.title.text = event.title
        holder.date.text = "Date: ${dateFormatter.format(eventTime)}"
        holder.venue.text = "Venue: ${event.venue}"



        holder.doti.visibility = if (now >= eventTime) View.VISIBLE else View.GONE

        holder.line.visibility = if (!isLastEvent && now >= endTime) View.VISIBLE else View.GONE

    }

    override fun getItemCount(): Int = events.size
}