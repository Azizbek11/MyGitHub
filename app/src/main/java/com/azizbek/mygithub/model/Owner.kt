package com.azizbek.mygithub.model

import com.google.gson.annotations.SerializedName


data class Owner (

         @SerializedName("login") var login : String,
         var id : Int,
         var nodeId : String,
         @SerializedName("avatar_url")var avatarUrl : String,
         var gravatarId : String,
         var url : String,
         var htmlUrl : String,
         @SerializedName("followers_url") var followersUrl : String,
         @SerializedName("following_url") var followingUrl : String,
         var gistsUrl : String,
         var starredUrl : String,
         var subscriptionsUrl : String,
         var organizationsUrl : String,
         var reposUrl : String,
         var eventsUrl : String,
         var receivedEventsUrl : String,
         var type : String,
         var siteAdmin : Boolean

)