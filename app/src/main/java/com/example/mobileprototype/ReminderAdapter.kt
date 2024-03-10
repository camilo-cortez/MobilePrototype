package com.example.mobileprototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class ReminderAdapter(var mList: List<ReminderData>): RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {
    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.titleTv)
        val artistTv : TextView = itemView.findViewById(R.id.titleTvArtist)
        val dateTv : TextView = itemView.findViewById(R.id.titleTvDate)
        val alarmTv : TextView = itemView.findViewById(R.id.titleTvAlarm)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false)
        return ReminderViewHolder(view)
    }

    fun setFilteredList(mList: List<ReminderData>){
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.title.text = mList[position].title
        holder.artistTv.text = mList[position].artist
        holder.dateTv.text = mList[position].date
        holder.alarmTv.text = mList[position].alarm
    }
}