package com.ninositsolution.paging.networks

import com.ninositsolution.paging.networks.responses.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("user")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<UserResponse>
}