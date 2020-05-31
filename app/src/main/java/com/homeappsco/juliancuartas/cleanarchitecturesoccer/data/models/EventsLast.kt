package com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models

import com.google.gson.annotations.SerializedName


data class EventsLast(
    @SerializedName("results")
    val eventTeams: List<EventTeam>
)