package com.ninositsolution.paging.data.repos

import com.ninositsolution.paging.networks.APIService
import com.ninositsolution.paging.networks.SafeApiRequest
import javax.inject.Inject

class MainRepo @Inject constructor(private val apiService: APIService) : SafeApiRequest() {

    suspend fun getUsers(page: Int, limit: Int = 15) =
        apiRequest { apiService.getUsers(page, limit) }
}