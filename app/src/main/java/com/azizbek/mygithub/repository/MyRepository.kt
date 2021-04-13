package com.azizbek.mygithub.repository

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.azizbek.mygithub.model.PopularRepo
import com.azizbek.mygithub.model.UserInfo
import com.azizbek.mygithub.network.ApiService
import com.azizbek.mygithub.network.ServicesBuilder
import retrofit2.Call
import retrofit2.Response

class MyRepository {
    private val request = ServicesBuilder.buildService(ApiService::class.java)
    private var haveData: MutableLiveData<Int> = MutableLiveData()
    private var userData: MutableLiveData<UserInfo> = MutableLiveData()
    private var recyclerListData: MutableLiveData<PopularRepo> = MutableLiveData()

    fun getAccountInfo(userName:String): LiveData<UserInfo> {

        val call = request.getUserData(userName)

        call.enqueue(object : retrofit2.Callback<UserInfo> {

            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    userData.postValue(response.body())

                } else {
                    userData.postValue(null)
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                userData.postValue(null)
            }
        })
        return userData
    }

    fun getPopularRepo(language:String, sort:String):LiveData<PopularRepo>{
        val call = request.getPopularRepo(language, sort)
        call.enqueue(object : retrofit2.Callback<PopularRepo> {

            override fun onResponse(call: Call<PopularRepo>, response: Response<PopularRepo>) {
                if (response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                    haveData.value= View.GONE
                }else{
                    recyclerListData.postValue(null)
                }
            }
            override fun onFailure(call: Call<PopularRepo>, t: Throwable) {
                recyclerListData.value=null
            }
        })
        return recyclerListData
    }
}