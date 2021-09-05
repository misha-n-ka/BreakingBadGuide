package com.breakingBadGuide.data.models

import com.google.gson.annotations.SerializedName


// Model for RecyclerView item
data class AllCharactersItem(
    @SerializedName("char_id")
    val charId: Int,
    val img: String,
    val name: String,
)