package com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.team_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TheTeamDBClient
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TeamApiService
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.NetworkState
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.SearchAllTeamsResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class TeamListViewModel ( private val teamListRepository: TeamListRepository) : ViewModel() {


    private val compisiteDisposable = CompositeDisposable()
    val apiService : TeamApiService = TheTeamDBClient.getClient()

    private val _teamList = MutableLiveData<SearchAllTeamsResponse>()
    val SearchAllTeamsResponse : LiveData<SearchAllTeamsResponse>
        get() = _teamList

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState


    init {
        changeTeam("Spanish La Liga")
    }

    override fun onCleared() {
        super.onCleared()
        compisiteDisposable.dispose()
    }

    fun changeTeam(team: String){

        _networkState.postValue(NetworkState.Loading())

        try {

            compisiteDisposable.add(
                apiService.getTeamsByLeague(team)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        _teamList.postValue(it)
                        _networkState.postValue(NetworkState.Loaded())
                    },{
                        _networkState.postValue(NetworkState.Error())
                        Log.e("TeamDetailsDataSource", it.message)
                    })
            )

        }catch (e: Exception){
            Log.e("TeamDetailsDataSourceEx", e.message)
        }
    }




}