package com.azizbek.mygithub.model

import com.google.gson.annotations.SerializedName


data class UserInfo (
        @SerializedName("login") var login : String,
        @SerializedName("avatar_url") var avatarUrl : String,
        @SerializedName("html_url") var htmlUrl : String,
        @SerializedName("followers_url") var followersUrl : String,
        @SerializedName("following_url") var followingUrl : String,
        @SerializedName("location") var location : String,
        @SerializedName("name") var name : String,
        @SerializedName("bio") var bio : String,
        @SerializedName("public_repos") var publicRepos : Int,
        @SerializedName("followers") var followersCount : Int,
        @SerializedName("following") var followingCount : Int,
        )