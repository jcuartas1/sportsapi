package com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api

import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.LookUpTeamResponse
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.SearchAllTeamsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TeamApiService {

    //https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=Spanish La Liga
    //https://www.thesportsdb.com/api/v1/json/1/eventsnext.php?id=133739
    //https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=133739

    @GET("lookupteam.php")
    fun getTeamDetails(@Query("id") id: Int):Single<LookUpTeamResponse> // (Single) This returns a single observable

    @GET("search_all_teams.php")
    fun getTeamsByLeague(@Query("l") id: String):Single<SearchAllTeamsResponse>


}