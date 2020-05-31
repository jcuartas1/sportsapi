package com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository

import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TeamApiService
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.EventTeam
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TeamEventsRepository(private val apiService : TeamApiService) {

    fun fetchAllEvents(idTeam: Int): Single<List<EventTeam>> {
        return apiService.getLastFiveEvents(idTeam)
            .flatMap { Single.just(it.eventTeams) }
            .subscribeOn(Schedulers.io())

    }
}