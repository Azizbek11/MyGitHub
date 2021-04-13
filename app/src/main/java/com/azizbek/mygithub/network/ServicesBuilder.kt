package com.azizbek.mygithub.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServicesBuilder {

    private const val BASE_URL = "https://api.github.com/"

    private var retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun<T> buildService(service:Class<T>):T{
        return retrofit.create(service)
    }

}