package com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.team_list

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.R
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.SearchAllTeamsResponse
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.single_team_details.SingleTeam
import kotlinx.android.synthetic.main.team_list_item.view.*

class ListTeamLeagueAdapter ( private val items : SearchAllTeamsResponse) : RecyclerView.Adapter<ListTeamLeagueAdapter.ViewHolder>() {


    override fun getItemCount(): Int = items.teamList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListTeamLeagueAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.team_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListTeamLeagueAdapter.ViewHolder, position: Int) {

        val value = items.teamList[position]

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
                val intent = Intent(holder.itemView.context, SingleTeam::class.java )

                intent.putExtra("id", value.idTeam.toInt())
                intent.putExtra("name", value.strAlternate)
                holder.itemView.context.startActivity(intent)
            }



    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val txt_team_name = itemView.txt_team_title
        val txt_stadium_name = itemView.txt_team_stadium
        val badge_team = itemView.img_team_poster

    }

}