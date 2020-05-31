package com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.single_team_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.R
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.EventTeam
import kotlinx.android.synthetic.main.event_list_item.view.*

class EventListAdapter(private val items: List<EventTeam>) : RecyclerView.Adapter<EventListAdapter.ViewHolder>() {


    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.event_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: EventListAdapter.ViewHolder, position: Int) {

        val value = items[position]

        holder.txt_event_title.text = value.strLeague
        holder.txt_event_description.text = value.strEvent
        holder.txt_home_team.text = value.strHomeTeam
        holder.txt_visit_team.text = value.strAwayTeam
        holder.txt_result_home.text = value.intHomeScore
        holder.txt_result_visit.text = value.intAwayScore

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val txt_event_title = itemView.event_title
        val txt_event_description = itemView.event_description
        val txt_home_team = itemView.home_team
        val txt_visit_team = itemView.visit_team
        val txt_result_home = itemView.home_result
        val txt_result_visit = itemView.visit_result


    }


}