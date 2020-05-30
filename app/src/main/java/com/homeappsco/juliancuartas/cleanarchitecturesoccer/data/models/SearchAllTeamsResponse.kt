package com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models


import com.google.gson.annotations.SerializedName

data class SearchAllTeamsResponse(
    @SerializedName("teams")
    val teamList: List<TeamDetail>
)