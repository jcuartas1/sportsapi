package com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository

import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TeamApiService
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.TeamDetail
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TeamListRepository (private val apiService : TeamApiService) {

    fun fetchTeamList(leagueName: String): Single<List<TeamDetail>> {
        return apiService.getTeamsByLeague(leagueName)
            .flatMap { Single.just(it.teamList) }
            .subscribeOn(Schedulers.io())
    }
}