package com.example.signet.network

import com.google.gson.annotations.SerializedName

data class ArticleMetaData (
    @SerializedName("result") val result: Result,
    @SerializedName("meta") val meta: Meta
)

data class Result (
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("reason") val reason: String
)

data class Site (
    @SerializedName("name") val name: String,
    @SerializedName("favicon") val favicon: String
)

data class Meta (
    @SerializedName("site") val site: Site,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("title") val title: String
)
