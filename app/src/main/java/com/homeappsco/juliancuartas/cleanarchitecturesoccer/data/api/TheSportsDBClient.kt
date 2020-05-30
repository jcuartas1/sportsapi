package com.homeappsco.juliancuartas.cleanarchitecturesoccer.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/"


object TheTeamDBClient {

    fun getClient():TeamApiService {

        val okHttpClient = OkHttpClient.Builder()

        val logggin = HttpLoggingInterceptor()
        logggin.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(logggin)

        return Retrofit.Builder()
            .client(okHttpClient.build())
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TeamApiService::class.java)

    }

}