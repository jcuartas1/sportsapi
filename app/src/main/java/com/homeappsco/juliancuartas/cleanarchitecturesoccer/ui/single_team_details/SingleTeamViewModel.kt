package com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.single_team_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.SchedulersProvider
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.applyIoToMainSchedulers
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.EventTeam
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.EventsLast
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.NetworkState
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.LookUpTeamResponse
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.Team
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.TeamDetailsRepository
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.TeamEventsRepository
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.TeamListRepository
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.safeDispose
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class SingleTeamViewModel (private val teamDetailsRepository: TeamDetailsRepository,
                           idTeam: Int, private val teamEventsRepository: TeamEventsRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _lookUpTeam = MutableLiveData<List<Team>>()
    val lookUpTeam : LiveData<List<Team>>
        get() = _lookUpTeam

    private val _eventLast = MutableLiveData<List<EventTeam>>()
    val eventLast : LiveData<List<EventTeam>>
        get() = _eventLast

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _teamName = MutableLiveData<String>()
    val teamName: LiveData<String>
        get() = _teamName

    private val _teamDescription = MutableLiveData<String>()
    val teamDescription: LiveData<String>
        get() = _teamDescription

    private val _teamFoundationYear = MutableLiveData<String>()
    val teamFoundationYear: LiveData<String>
        get() = _teamFoundationYear

    private val _urlFanArt = MutableLiveData<String>()
    val urlFanArt: LiveData<String>
        get() = _urlFanArt

    private val _urlBadge = MutableLiveData<String>()
    val urlBadge: LiveData<String>
        get() = _urlBadge

    private val _urlJersey = MutableLiveData<String>()
    val urlJersey: LiveData<String>
        get() = _urlJersey

    init {
        getTeamDetail(idTeam)
        getEventTeam(idTeam)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.safeDispose()
    }

    fun getTeamDetail(teamId: Int){

        _networkState.postValue(NetworkState.Loading())

        compositeDisposable.add(
            teamDetailsRepository.fetchTeamDetails(teamId)
                .applyIoToMainSchedulers(SchedulersProvider)
                .subscribe({
                bindUi(it)
                    _lookUpTeam.value = it
                _networkState.postValue(NetworkState.Loaded())
            },{
                _networkState.postValue(NetworkState.Error())
            })
        )


    }

    private fun getEventTeam(teamId: Int){

        compositeDisposable.add(
            teamEventsRepository.fetchAllEvents(teamId)
                .applyIoToMainSchedulers(SchedulersProvider)
                .subscribe({
                    _eventLast.value = it
                },{
                    _networkState.postValue(NetworkState.Error())
                })
        )


    }

    private fun bindUi(team: List<Team>){

        for(value in team){
            _teamName.value = value.strAlternate
            _teamDescription.value = value.strDescriptionEN
            _teamFoundationYear.value = value.intFormedYear
            _urlFanArt.value = value.strTeamFanart1
            _urlBadge.value = value.strTeamBadge
            _urlJersey.value = value.strTeamJersey

        }

    }

}