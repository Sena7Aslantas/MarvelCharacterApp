package com.example.marvellistapp.data.remote.model.comics

import com.google.gson.annotations.SerializedName

data class Url(
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)
