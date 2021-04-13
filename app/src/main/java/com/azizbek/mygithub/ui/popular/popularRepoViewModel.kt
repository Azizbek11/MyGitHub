package com.azizbek.mygithub.ui.popular


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.azizbek.mygithub.model.PopularRepo
import com.azizbek.mygithub.repository.MyRepository

class popularRepoViewModel : ViewModel() {

    fun getRepo(language: String,sort: String): LiveData<PopularRepo> {
        return MyRepository().getPopularRepo(language, sort)

    }
}