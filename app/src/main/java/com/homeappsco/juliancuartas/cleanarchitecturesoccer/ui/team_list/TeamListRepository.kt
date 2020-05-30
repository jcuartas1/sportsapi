package com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.team_list

import androidx.lifecycle.LiveData
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TeamApiService
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.NetworkState
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.TeamDataSource
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.SearchAllTeamsResponse
import io.reactivex.disposables.CompositeDisposable

//TODO eliminar esta clase

class TeamListRepository (private val apiService: TeamApiService) {

    lateinit var teamListDataSource : TeamDataSource

    fun fetchListTeam(compositeDisposable: CompositeDisposable, leagueName: String) : LiveData<SearchAllTeamsResponse>{

        teamListDataSource = TeamDataSource(apiService, compositeDisposable)
        teamListDataSource.fetchTeamList(leagueName)

        return teamListDataSource.downloadedSearchAllTeamsResponse

    }

    fun getTeamDetailsNetworkState(): LiveData<NetworkState> {

        return teamListDataSource.networkState

    }

}