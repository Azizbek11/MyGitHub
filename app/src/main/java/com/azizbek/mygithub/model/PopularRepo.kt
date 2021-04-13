package com.azizbek.mygithub.model

import com.google.gson.annotations.SerializedName


data class PopularRepo (
        @SerializedName("total_count")var totalCount : Int,
        @SerializedName("incomplete_results")var incompleteResults : Boolean,
        @SerializedName("items")var items : List<Items>
)