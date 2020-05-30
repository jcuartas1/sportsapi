package com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.single_team_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.NetworkState
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.LookUpTeamResponse
import io.reactivex.disposables.CompositeDisposable

class SingleTeamViewModel (private val teamDetailsRepository: TeamDetailsRepository, teamId: Int) : ViewModel() {

    private  val compisiteDisposable = CompositeDisposable()

    val lookUpTeamResponse : LiveData<LookUpTeamResponse> by lazy {
        teamDetailsRepository.fetchSingleTeamDetails(compisiteDisposable, teamId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        teamDetailsRepository.getTeamDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compisiteDisposable.dispose()
    }

}