package com.azizbek.mygithub.ui.account

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.azizbek.mygithub.model.UserInfo
import com.azizbek.mygithub.repository.MyRepository

@SuppressLint("StaticFieldLeak")
class AccountViewModel(application: Application) : AndroidViewModel(application) {

     val context: Context =getApplication<Application>().applicationContext
    fun shareUserAccount(){
        Toast.makeText(context,"Hello"+userData?.value?.htmlUrl,Toast.LENGTH_SHORT).show()
        val share = Intent(Intent.ACTION_SEND)
        share.putExtra(Intent.EXTRA_TEXT,"Hey friend! Check out this repository:)\n"+ userData?.value?.htmlUrl)
        share.type = "text/plain"
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(share)
    }

    private var userData: LiveData<UserInfo>? = null
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("githubname", Context.MODE_PRIVATE)
    var userName=sharedPreferences.getString("githubUserName",null )
    init {
        userData= MyRepository().getAccountInfo(userName!!)
    }

    fun getUserData(): LiveData<UserInfo> {
        return userData!!
    }
}