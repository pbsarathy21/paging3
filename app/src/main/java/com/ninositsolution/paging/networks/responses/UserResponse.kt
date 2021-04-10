package com.ninositsolution.paging.networks.responses

import com.google.gson.annotations.SerializedName
import com.ninositsolution.paging.data.models.User

data class UserResponse(
    @SerializedName("data") val users: List<User>,
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("offset") val offset: Int,
)
