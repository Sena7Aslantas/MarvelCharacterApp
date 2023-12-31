package com.example.marvellistapp.data.remote.model.comics

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("count")
    val count: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val results: List<ResultDto>,
    @SerializedName("total")
    val total: Int
)
