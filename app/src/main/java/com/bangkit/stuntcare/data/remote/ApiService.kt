package com.bangkit.stuntcare.data.remote

import com.bangkit.stuntcare.data.remote.response.ChildrenResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    // TODO
    @GET("child/{id}")
    suspend fun getChildrenById(
        @Path("id") id: String
    ): ChildrenResponse
}