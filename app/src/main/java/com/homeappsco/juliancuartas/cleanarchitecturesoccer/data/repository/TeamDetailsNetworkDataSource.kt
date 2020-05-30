package com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TeamApiService
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.LookUpTeamResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class TeamDetailsNetworkDataSource
    (private val apiService : TeamApiService,
     private val compositeDisposable: CompositeDisposable ) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedTeamDetailsResponse = MutableLiveData<LookUpTeamResponse>()
    val downloadedLookUpTeamResponseResponse: LiveData<LookUpTeamResponse>
        get() = _downloadedTeamDetailsResponse

    fun fetchTeamDetails(teamId: Int){

        _networkState.postValue(NetworkState.Loading())

        try {

            compositeDisposable.add(
                apiService.getTeamDetails(teamId)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                            _downloadedTeamDetailsResponse.postValue(it)
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