package com.breakingBadGuide.data.models

import com.google.gson.annotations.SerializedName

// Model for DetailFragment
data class CharacterByIdItem(
    val appearance: List<Int>,
    @SerializedName("better_call_saul_appearance")
    val betterCallSaulAppearance: List<Int>,
    val birthday: String,
    val category: String,
    val char_id: Int,
    val img: String,
    val name: String,
    val nickname: String,
    val occupation: List<String>,
    val portrayed: String,
    val status: String
)