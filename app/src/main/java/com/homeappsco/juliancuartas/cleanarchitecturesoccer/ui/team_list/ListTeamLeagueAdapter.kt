package com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.team_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.R
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.TeamDetail
import kotlinx.android.synthetic.main.team_list_item.view.*

class ListTeamLeagueAdapter ( private val items : List<TeamDetail>, private val onClick:(Int, String) -> Unit) : RecyclerView.Adapter<ListTeamLeagueAdapter.ViewHolder>() {


    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListTeamLeagueAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.team_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListTeamLeagueAdapter.ViewHolder, position: Int) {

        val value = items[position]

            holder.txt_team_name.text = value.strAlternate
            holder.txt_stadium_name.text = value.strStadium

            if(value.strTeamBadge.isEmpty()){
                Glide.with(holder.itemView.context)
                    .load(R.drawable.poster_placeholder)
                    .into(holder.badge_team)
            } else {

                val teamBadgeURL = value.strTeamBadge
                Glide.with(holder.itemView.context)
                    .load(teamBadgeURL)
                    .into(holder.badge_team)

            }

            holder.itemView.setOnClickListener {

                onClick(value.idTeam.toInt(), value.strTeam)

            }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val txt_team_name = itemView.txt_team_title
        val txt_stadium_name = itemView.txt_team_stadium
        val badge_team = itemView.img_team_poster

    }

}