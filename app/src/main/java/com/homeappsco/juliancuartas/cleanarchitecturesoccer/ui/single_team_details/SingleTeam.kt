package com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.single_team_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.R
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TeamApiService
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TheTeamDBClient
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.Team
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.NetworkState
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.TeamDetailsRepository
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.TeamEventsRepository
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.databinding.CollapsingToolbarBinding
import kotlinx.android.synthetic.main.collapsing_toolbar.*

class SingleTeam : AppCompatActivity() {

    private lateinit var viewModel: SingleTeamViewModel
    private lateinit var teamDetailsRepository: TeamDetailsRepository
    private lateinit var binding: CollapsingToolbarBinding
    private lateinit var teamEventsRepository: TeamEventsRepository

    private var teamId : Int = 0
    private var facebook: String = ""
    private var instagram: String = ""
    private var twitter: String = ""
    private var webUrl: String = ""
    private var titleTeam: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CollapsingToolbarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        titleTeam = intent.getStringExtra("name")

        toolbarDetails.title = titleTeam
        setSupportActionBar(toolbarDetails)

        teamId  = intent.getIntExtra("id", 1)

        val apiService : TeamApiService = TheTeamDBClient.getClient()

        teamDetailsRepository = TeamDetailsRepository(apiService)

        teamEventsRepository = TeamEventsRepository(apiService)

        viewModel = getViewModel(teamId)

        binding.viewModelLisTeam = viewModel
        binding.lifecycleOwner = this

        viewModel.lookUpTeam.observe(this, Observer {
            socialUI(it)
        })

        viewModel.eventLast.observe(this, Observer {
            val adapterEvent = EventListAdapter(it)
            val linearLayoutManager = LinearLayoutManager(this)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.rvEventList.layoutManager = linearLayoutManager
            binding.rvEventList.setHasFixedSize(true)
            binding.rvEventList.adapter = adapterEvent
        })

        viewModel.networkState.observe(this, Observer {
            binding.progressBar.visibility = if (it == NetworkState.Loading()) View.VISIBLE else View.GONE
            binding.txtError.visibility = if (it == NetworkState.Error()) View.VISIBLE else View.GONE
        })
    }

    fun socialUI(teams : List<Team>){

        for( value in teams ){

            facebook = value.strFacebook
            instagram = value.strInstagram
            twitter = value.strTwitter
            webUrl = value.strWebsite

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.social_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.social_facebook -> {
               if(facebook != ""){
                    sendSocialLinks(facebook)
               }else{
                   Toast.makeText(this, "Opps! something is wrong", Toast.LENGTH_LONG).show()
               }
            }

            R.id.social_Instagram -> {
                if(instagram != ""){
                    sendSocialLinks(instagram)
                }else{
                    Toast.makeText(this, "Opps! something is wrong", Toast.LENGTH_LONG).show()
                }
            }

            R.id.social_twitter -> {
                if(twitter != ""){
                    sendSocialLinks(twitter)
                }else{
                    Toast.makeText(this, "Opps! something is wrong", Toast.LENGTH_LONG).show()
                }
            }

            R.id.social_url -> {
                if(webUrl != ""){
                    sendSocialLinks(webUrl)
                }else{
                    Toast.makeText(this, "Opps! something is wrong", Toast.LENGTH_LONG).show()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getViewModel(teamId: Int) : SingleTeamViewModel{

        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>) : T {
                @Suppress("UNCHECKED_CAST")
                return SingleTeamViewModel(teamDetailsRepository, teamId, teamEventsRepository) as T
            }
        })[SingleTeamViewModel::class.java]

    }

    private fun sendSocialLinks(social: String){
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://"+social))
            startActivity(intent)

        }catch (e: Exception){
            Log.d("ERROR LINK", e.toString())
        }
    }

}
