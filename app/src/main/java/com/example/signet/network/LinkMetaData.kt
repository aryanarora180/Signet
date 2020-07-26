package com.example.signet.network

import com.google.gson.annotations.SerializedName

data class LinkMetaData (
    @SerializedName("result") val result: Result = Result(),
    @SerializedName("meta") val meta: Meta = Meta()
)

data class Result (
    @SerializedName("status") val status: String = "",
    @SerializedName("code") val code: Int = 100,
    @SerializedName("reason") val reason: String = ""
)

data class Site (
    @SerializedName("name") val name: String = "name",
    @SerializedName("favicon") val favicon: String = "favicon"
)

data class Meta (
    @SerializedName("site") val site: Site = Site(),
    @SerializedName("description") val description: String = "description",
    @SerializedName("image") val image: String = "image",
    @SerializedName("title") val title: String = "title"
)
