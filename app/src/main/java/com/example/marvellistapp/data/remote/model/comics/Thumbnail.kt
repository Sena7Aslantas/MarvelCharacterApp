package com.example.marvellistapp.data.remote.model.comics

import com.google.gson.annotations.SerializedName

data class Thumbnail (
    @SerializedName("extension")
    val extension: String,
    @SerializedName("path")
    val path: String
)