package com.azizbek.mygithub.ui.home

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.agrawalsuneet.dotsloader.basicviews.CircularLoaderBaseView
import com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader
import com.azizbek.mygithub.R
import com.azizbek.mygithub.adapter.UserRepoAdapter
import com.azizbek.mygithub.model.PopularRepo
import com.azizbek.mygithub.network.ApiService
import com.azizbek.mygithub.network.ServicesBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Response

class HomeViewModel(application:Application) : AndroidViewModel(application) {

    private var recyclerListData: MutableLiveData<PopularRepo> = MutableLiveData()
    private var haveData: MutableLiveData<Int> = MutableLiveData()
    private lateinit var language: String
    private lateinit var sort: String

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
        val call = request.getPopularRepo(language, sort)
        call.enqueue(object : retrofit2.Callback<PopularRepo> {

            override fun onResponse(call: Call<PopularRepo>, response: Response<PopularRepo>) {
                if (response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                    haveData.postValue(View.GONE)
                }else{
                    recyclerListData.postValue(null)
                }
            }
            override fun onFailure(call: Call<PopularRepo>, t: Throwable) {
                recyclerListData.postValue(null)
            }
        })
    }

}