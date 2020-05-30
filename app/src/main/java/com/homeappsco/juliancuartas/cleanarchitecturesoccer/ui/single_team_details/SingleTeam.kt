package com.homeappsco.juliancuartas.cleanarchitecturesoccer.ui.single_team_details

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.R
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TheTeamDBClient
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api.TeamApiService
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.repository.NetworkState
import com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.models.LookUpTeamResponse
import kotlinx.android.synthetic.main.collapsing_toolbar.*

class SingleTeam : AppCompatActivity() {

    private lateinit var viewModel: SingleTeamViewModel
    private lateinit var teamDetailsRepository: TeamDetailsRepository

    private var teamId : Int = 0
    private var facebook: String = ""
    private var instagram: String = ""
    private var twitter: String = ""
    private var webUrl: String = ""
    private var titleTeam: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collapsing_toolbar)

        titleTeam = intent.getStringExtra("name")

        toolbarDetails.title = titleTeam
        setSupportActionBar(toolbarDetails)

        teamId  = intent.getIntExtra("id", 1)

        val apiService : TeamApiService = TheTeamDBClient.getClient()

        teamDetailsRepository = TeamDetailsRepository(apiService)

        viewModel = getViewModel(teamId)

        viewModel.lookUpTeamResponse.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.Loading()) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.Error()) View.VISIBLE else View.GONE
        })
    }

    fun bindUI(it : LookUpTeamResponse){

        for( value in it.teams ){

            team_title.text = value.strAlternate
            team_description.text = value.strDescriptionEN
            team_foundation_year.text = value.intFormedYear
            titleTeam = value.strAlternate
            facebook = value.strFacebook
            instagram = value.strInstagram
            twitter = value.strTwitter
            webUrl = value.strWebsite

            val teamImageClub = value.strTeamFanart2
            Glide.with(this)
                .load(teamImageClub)
                .centerCrop()
                .placeholder(R.drawable.poster_placeholder)
                .into(img_club)

            val teamBadgeURL = value.strTeamBadge
            Glide.with(this)
                .load(teamBadgeURL)
                .into(img_badge)

            val teamImageJersey = value.strTeamJersey
            Glide.with(this)
                .load(teamImageJersey)
                .into(img_jersey)

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
                return SingleTeamViewModel(teamDetailsRepository, teamId) as T
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
