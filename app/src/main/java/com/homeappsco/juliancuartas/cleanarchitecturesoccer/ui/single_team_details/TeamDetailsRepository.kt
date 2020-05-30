package com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.single_team_details

import androidx.lifecycle.LiveData
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TeamApiService
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.NetworkState
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.TeamDetailsNetworkDataSource
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.LookUpTeamResponse
import io.reactivex.disposables.CompositeDisposable

class TeamDetailsRepository (private val apiService: TeamApiService) {

    lateinit var teamDetailsNetworkDataSource: TeamDetailsNetworkDataSource

    fun fetchSingleTeamDetails (compositeDisposable: CompositeDisposable, teamId: Int) : LiveData<LookUpTeamResponse>{

        teamDetailsNetworkDataSource = TeamDetailsNetworkDataSource(apiService, compositeDisposable)
        teamDetailsNetworkDataSource.fetchTeamDetails(teamId)


        return teamDetailsNetworkDataSource.downloadedLookUpTeamResponseResponse

    }

    fun getTeamDetailsNetworkState(): LiveData<NetworkState> {

        return teamDetailsNetworkDataSource.networkState

    }

}