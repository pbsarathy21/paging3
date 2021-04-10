package com.ninositsolution.paging.data.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("picture") val picture: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("email") val email: String,
)
