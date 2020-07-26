package com.example.signet.helper

import java.util.*

data class Link(
    val linkUrl: String,
    val featuredImageUrl: String,
    val faviconUrl: String,
    val title: String,
    val dateAdded: Long,
    val siteName: String
)