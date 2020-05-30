package com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.R
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TheTeamDBClient
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TeamApiService
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.NetworkState
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.team_list.ListTeamLeagueAdapter
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.team_list.TeamListRepository
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.team_list.TeamListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : TeamListViewModel

    lateinit var teamListRepository: TeamListRepository

    //var toolbar : Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = getString(R.string.mainToolbarTitle)
        setSupportActionBar(toolbar)

        val apiService : TeamApiService = TheTeamDBClient.getClient()
        teamListRepository = TeamListRepository(apiService)

        viewModel = getViewModel()

        val gridLayout = GridLayoutManager(this,3)

        viewModel.SearchAllTeamsResponse.observe(this, Observer {


            val adapterList = ListTeamLeagueAdapter(it)

            gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
                override fun getSpanSize(position: Int): Int {
                    adapterList.getItemViewType(position)
                    return 1
                }
            }

            rv_team_list.layoutManager = gridLayout
            rv_team_list.setHasFixedSize(true)
            rv_team_list.adapter = adapterList

        })

        viewModel.networkState.observe(this, Observer {

            progress_bar_team_list.visibility = if (it == NetworkState.Loading()) View.VISIBLE else View.GONE
            txt_error_team_list.visibility = if (it == NetworkState.Error()) View.VISIBLE else View.GONE
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.germany_league -> {
               viewModel.changeTeam(getString(R.string.germanLeague))
            }

            R.id.italy_league -> {
                viewModel.changeTeam(getString(R.string.italyLeague))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getViewModel() : TeamListViewModel {

        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>) : T {
                @Suppress("UNCHECKED_CAST")
                return TeamListViewModel(teamListRepository) as T
            }
        })[TeamListViewModel::class.java]

    }
}
