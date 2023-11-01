package com.example.marvellistapp.data.remote.model.comics

import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("price")
    val price: Double,
    @SerializedName("type")
    val type: String
)
