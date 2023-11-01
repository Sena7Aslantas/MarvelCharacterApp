package com.example.marvellistapp.domain.model

import com.example.marvellistapp.data.remote.model.comics.Date
import com.example.marvellistapp.data.remote.model.comics.Thumbnail
import com.google.gson.annotations.SerializedName

data class Comics(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("dates")
    val dates: List<Date>,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail,
    var date: String?
)
