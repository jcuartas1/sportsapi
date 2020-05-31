package com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository

import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TeamApiService
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.Team
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TeamDetailsRepository
    (private val apiService : TeamApiService) {

    fun fetchTeamDetails(teamId: Int): Single<List<Team>> {
              return apiService.getTeamDetails(teamId)
                    .flatMap { Single.just(it.teams) }
                    .subscribeOn(Schedulers.io())
    }

}