package com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TeamApiService
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.SearchAllTeamsResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

//TODO eliminar esta clase

class TeamDataSource (private val apiService : TeamApiService,
                      private val compositeDisposable: CompositeDisposable){


    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedTeamList = MutableLiveData<SearchAllTeamsResponse>()
    val downloadedSearchAllTeamsResponse: LiveData<SearchAllTeamsResponse>
        get() = _downloadedTeamList


    fun fetchTeamList(leagueName: String){

        //_networkState.postValue(NetworkState.Loading())

        try {

            compositeDisposable.add(
                apiService.getTeamsByLeague(leagueName)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        _downloadedTeamList.postValue(it)
                        _networkState.postValue(NetworkState.Loaded())
                    },{
                        _networkState.postValue(NetworkState.Error())
                        Log.e("TeamDetailsDataSource", it.message)
                    }
                    )
            )

        }catch (e: Exception){
            Log.e("TeamDetailsDataSourceEx", e.message)
        }

    }
}