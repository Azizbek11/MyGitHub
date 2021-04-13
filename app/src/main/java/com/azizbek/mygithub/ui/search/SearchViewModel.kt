package com.azizbek.mygithub.ui.search

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azizbek.mygithub.model.PopularRepo
import com.azizbek.mygithub.network.ApiService
import com.azizbek.mygithub.network.ServicesBuilder
import retrofit2.Call
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private var recyclerListData: MutableLiveData<PopularRepo> = MutableLiveData()
    private var haveData: MutableLiveData<Int> = MutableLiveData()
    private lateinit var language: String
    private lateinit var sort: String
    private lateinit var order: String


    fun getRecyclerListData(language:String, sort:String):MutableLiveData<PopularRepo> {
        this.language=language
        this.sort=sort
        return recyclerListData
    }

    fun responseHaveData():MutableLiveData<Int>{
        return haveData
    }

    fun makeApiCall(){
        val request = ServicesBuilder.buildService(ApiService::class.java)
        val call = request.getSearchResult(language, sort)
        call.enqueue(object : retrofit2.Callback<PopularRepo> {

            override fun onResponse(call: Call<PopularRepo>, response: Response<PopularRepo>) {
                if (response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                    haveData.value=View.GONE

                }else{
                    recyclerListData.postValue(null)
                }
            }
            override fun onFailure(call: Call<PopularRepo>, t: Throwable) {
                recyclerListData.value=null
            }
        })
    }
}