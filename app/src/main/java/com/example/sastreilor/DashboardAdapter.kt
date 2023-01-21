package com.example.sastreilor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DashboardAdapter(private val context: Context) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 0){
            val view = LayoutInflater.from(context).inflate(R.layout.dashboard_item,parent,false)
            return ViewHolder(view)
        }else if (viewType == 1){
            val view = LayoutInflater.from(context).inflate(R.layout.dasboard_item_scrollable,parent,false)
            return ViewHolder(view)
        } else if (viewType == 2){
            val view = LayoutInflater.from(context).inflate(R.layout.dasboard_item_delivered,parent,false)
            return ViewHolder(view)
        }else if (viewType == 3){
            val view = LayoutInflater.from(context).inflate(R.layout.dashboard_item_task,parent,false)
            return ViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.dashboard_item_pastdue,parent,false)
            return ViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
//        return super.getItemViewType(position)
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val journal = journals[position]
//        holder.bind(journal)
    }

    override fun getItemCount() = 5
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
//        private val journalentryTextview = itemView.findViewById<TextView>(R.id.journalEntryTV)
//        private val journalTitlet = itemView.findViewById<TextView>(R.id.journaltitleTextV)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
//            val quote = journals[absoluteAdapterPosition]
        }

//        fun bind(journal: DisplayJournals) {
//            journalentryTextview.text = journal.journalEntry
//            journalTitlet.text = journal.journalTitle
//
//
//        }


    }
}