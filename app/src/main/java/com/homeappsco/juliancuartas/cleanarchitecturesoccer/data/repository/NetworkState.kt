package com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository



sealed class NetworkState  {

    data class Loaded(val message : String = "Success") : NetworkState()
    data class Loading(val message : String = "Running") : NetworkState()
    data class Error(val error : String = "Something went wrong") : NetworkState()

}
