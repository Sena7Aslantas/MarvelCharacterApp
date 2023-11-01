package com.example.marvellistapp.domain.model

import com.example.marvellistapp.data.remote.model.dto.Comics
import com.example.marvellistapp.data.remote.model.dto.Thumbnail
import com.google.gson.annotations.SerializedName

data class MarvelCharacter(
    @SerializedName("id")
    val id: Int,
    @SerializedName("comics")
    val comics: Comics,
    @SerializedName("description")
    val description: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail
)
