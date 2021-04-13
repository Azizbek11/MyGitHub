package com.azizbek.mygithub.ui.account

import android.app.Application
import android.content.Intent
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azizbek.mygithub.R
import com.azizbek.mygithub.model.UserInfo
import com.azizbek.mygithub.network.ApiService
import com.azizbek.mygithub.network.ServicesBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Response

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    private val context=getApplication<Application>().applicationContext

    private var userData: MutableLiveData<UserInfo> = MutableLiveData()

    private lateinit var userName: String

    fun getUserData(userName:String,):MutableLiveData<UserInfo> {
        this.userName=userName
        return userData
    }


    fun shareUserAccount(){
        Toast.makeText(context,"Hello"+userData.value?.htmlUrl,Toast.LENGTH_SHORT).show()
        val share = Intent(Intent.ACTION_SEND)
        share.putExtra(Intent.EXTRA_TEXT,"Hey friend! Check out this repository:)\n"+userData.value?.htmlUrl)
        share.type = "text/plain"
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(share)
    }

    fun makeApiCall(){
        val request = ServicesBuilder.buildService(ApiService::class.java)
        val call = request.getUserData(userName)

        call.enqueue(object : retrofit2.Callback<UserInfo> {

            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    userData.postValue(response.body())

                }else{
                    userData.postValue(null)
                }
            }
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                userData.postValue(null)
            }
        })
    }
}