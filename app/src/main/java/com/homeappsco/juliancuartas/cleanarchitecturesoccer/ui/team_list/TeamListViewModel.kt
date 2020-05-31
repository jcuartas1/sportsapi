package com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.team_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.SchedulersProvider
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.applyIoToMainSchedulers
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.TeamDetail
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.NetworkState
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.TeamListRepository
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.safeDispose
import io.reactivex.disposables.CompositeDisposable

class TeamListViewModel ( private val teamListRepository: TeamListRepository) : ViewModel() {


    private val compositeDisposable = CompositeDisposable()

    private val _teamList = MutableLiveData<List<TeamDetail>>()
    val teamList : LiveData<List<TeamDetail>>
        get() = _teamList

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState


    init {
        changeTeam("Spanish La Liga")
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.safeDispose()
    }

    fun changeTeam(team: String){

        _networkState.postValue(NetworkState.Loading())

        compositeDisposable.add(
            teamListRepository.fetchTeamList(team)
                .applyIoToMainSchedulers(SchedulersProvider)
                .subscribe({
                    _teamList.value = it
                    _networkState.postValue(NetworkState.Loaded())
                },{
                    _networkState.postValue(NetworkState.Error())
                })

        )

    }




}