package com.cevlikalprn.harrypotterwizards.model

import com.google.gson.annotations.SerializedName

data class Wizard(
    @SerializedName("alive")
    val alive: Boolean,
    @SerializedName("ancestry")
    val ancestry: String,
    @SerializedName("house")
    val house: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("yearOfBirth")
    val yearOfBirth: String
)