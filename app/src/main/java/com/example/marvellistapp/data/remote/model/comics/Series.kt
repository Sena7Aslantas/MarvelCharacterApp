package com.example.marvellistapp.data.remote.model.comics

import com.google.gson.annotations.SerializedName

data class Series(
    @SerializedName("name")
    val name: String,
    @SerializedName("resourceURI")
    val resourceURI: String
)
