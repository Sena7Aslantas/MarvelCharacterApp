package com.example.marvellistapp.data.remote.model.comics

import com.google.gson.annotations.SerializedName

data class ItemXXComics(
    @SerializedName("name")
    val name: String,
    @SerializedName("resourceURI")
    val resourceURI: String,
    @SerializedName("type")
    val type: String
)
